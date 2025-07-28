package com.aladigis.spotnik.ingestion.service

import com.aladigis.spotnik.ingestion.config.SpotnikIngestionConfiguration
import com.aladigis.spotnik.ingestion.model.LinkedEntity
import com.aladigis.spotnik.ingestion.model.LinkedLabel
import com.aladigis.spotnik.ingestion.port.IngestionPort
import com.aladigis.spotnik.ingestion.port.data.LinkedEntityDataPort
import com.aladigis.spotnik.ingestion.port.data.LinkedLabelDataPort
import com.aladigis.spotnik.ingestion.service.component.LinkedEntityProcessor
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.apache.commons.compress.compressors.bzip2.BZip2CompressorInputStream
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader
import java.util.zip.ZipException

@Service
class IngestionService : IngestionPort {
    @Autowired
    private lateinit var linkedEntityDataPort: LinkedEntityDataPort

    @Autowired
    private lateinit var linkedLabelDataPort: LinkedLabelDataPort

    @Autowired
    private lateinit var appConfig: SpotnikIngestionConfiguration

    @Autowired
    private lateinit var linkedEntityProcessor: LinkedEntityProcessor

    private val logger = LoggerFactory.getLogger(this::class.java)

    private val objectMapper = jacksonObjectMapper()

    private var processedBatches = 0

    companion object {
        private const val MAX_QUEUE_SIZE = 20
    }

    var startTime = 0L

    override fun ingest(
        fileName: String,
        fromLine: Int,
        toLine: Int,
    ) {
        startTime = System.currentTimeMillis()

        val file = File(fileName)
        if (!file.exists()) {
            throw IllegalArgumentException("File not found: $fileName")
        }

        logger.info("Starting ingestion for file: $fileName")

        val reader: BufferedReader =
            if (fileName.endsWith(".bz2")) {
                try {
                    BufferedReader(InputStreamReader(BZip2CompressorInputStream(file.inputStream())))
                } catch (e: ZipException) {
                    throw IllegalArgumentException("Failed to decompress .bz2 file: $fileName", e)
                }
            } else {
                file.bufferedReader()
            }

        var counter = -1
        val batchSize = appConfig.batchSize
        var batchNumber = 1

        val lines = mutableListOf<String>()
        logger.info("Processing file: $fileName with block size: $batchSize, from line: $fromLine to line: $toLine.")

        reader.use { bufferedReader ->
            bufferedReader.lines().forEach { line ->
                counter++
                batchNumber = counter / batchSize + 1

                if (fromLine > 0) {
                    if (counter < fromLine) {
                        print("\rskipping line number $counter to reach line number $fromLine.")
                        return@forEach
                    }
                }

                val trimmedLine = line.trim()
                if (trimmedLine.isEmpty() || trimmedLine == "[" || trimmedLine == "]") return@forEach

                val jsonLine = if (trimmedLine.endsWith(",")) trimmedLine.dropLast(1) else trimmedLine

                lines.add(jsonLine)

                if (toLine < Int.MAX_VALUE && counter >= toLine) {
                    logger.info("Reached the end line $toLine. Stopping processing.")
                    if (lines.isNotEmpty()) {
                        while (batchNumber - processedBatches > MAX_QUEUE_SIZE) {
                            Thread.sleep(1000L)
                            logger.info(
                                "Waiting for batch processing to catch up. Current batch number: $batchNumber, " +
                                    "processed batches: $processedBatches.",
                            )
                        }
                        startBatchProcessing(counter / batchSize + 1, lines)
                    }
                    return@forEach
                }

                if (counter % batchSize == 0) {
                    startBatchProcessing(batchNumber, lines.toList())
                    lines.clear()
                }
            }
        }
        // End of file, publish any remaining lines
        if (lines.isNotEmpty()) {
            startBatchProcessing(batchNumber, lines)
        }
        logger.info("Processed $counter items from file: $fileName in ${(System.currentTimeMillis() - startTime) / 1000.0} seconds.")
    }

    fun startBatchProcessing(
        batchNumber: Int,
        lines: List<String>,
    ) {
        Thread {
            try {
                processBatch(batchNumber, lines)
            } catch (e: Exception) {
                logger.error("Error processing batch $batchNumber: ${e.message}", e)
            }
        }.start()
    }

    fun processBatch(
        batchNumber: Int,
        lines: List<String>,
    ) {
        val entities = mutableListOf<LinkedEntity>()
        val labels = mutableListOf<LinkedLabel>()
        lines.forEach { line ->
            try {
                val rawItem: JsonNode = objectMapper.readTree(line)
                var linkedEntity = linkedEntityProcessor.parseEntity(rawItem)
                if ((linkedEntity != null)) {
                    if (linkedEntity.spacyTypes.isEmpty()) {
                        // First ingestion of this entity
                        linkedEntity = linkedEntityProcessor.enrichIfRelevant(linkedEntity) ?: return@forEach
                    }
                    entities.add(linkedEntity)
                    val entityLabels = linkedEntityProcessor.extractLabels(linkedEntity.id, rawItem)
                    labels.addAll(entityLabels)
                }
            } catch (e: Exception) {
                logger.warn("Skipping malformed JSON line: ${line.take(100)}. Error: ${e.message}")
            }
        }

        linkedEntityDataPort.saveAll(entities)
        linkedLabelDataPort.saveAll(labels)
        // safely increment the processed batches count
        synchronized(this) {
            processedBatches++
        }
        logger.info(
            "Batch $batchNumber processed with ${entities.size} entities and ${labels.size} labels. " +
                "Time since start: ${(System.currentTimeMillis() - startTime) / 1000.0} seconds.",
        )
    }
}
