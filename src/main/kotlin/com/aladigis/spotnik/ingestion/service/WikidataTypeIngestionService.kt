package com.aladigis.spotnik.ingestion.service

import com.aladigis.spotnik.ingestion.config.NerConfig
import com.aladigis.spotnik.ingestion.model.WikidataType
import com.aladigis.spotnik.ingestion.port.WikdataTypeIngestionPort
import com.aladigis.spotnik.ingestion.port.data.WikidataTypeDataPort
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.io.File

@Service
class WikidataTypeIngestionService : WikdataTypeIngestionPort {
    companion object {
        private const val BATCH_SIZE = 1000
    }

    private val logger = LoggerFactory.getLogger(this::class.java)

    @Autowired
    private lateinit var nerConfig: NerConfig

    @Autowired
    private lateinit var wikidataTypeDataPort: WikidataTypeDataPort

    override fun ingestSubtypes(fileName: String) {
        val startTime = System.currentTimeMillis()
        val file = File(fileName)
        if (!file.exists()) {
            throw IllegalArgumentException("File not found: $fileName")
        }

        logger.info("Starting ingestion of types from file: $fileName")

        val types = mutableListOf<WikidataType>()
        file.bufferedReader().use { reader ->
            reader.lines().forEach { line ->
                val parts = line.split("\t")
                if (parts.size < 3) return@forEach
                val rootType = parts[0].trim()
                val subtype = parts[1].trim()
                val label = parts[2].trim()
                val spacyType = nerConfig.getSpacyType(rootType)
                types.add(
                    WikidataType(
                        rootTypes = listOf(rootType),
                        id = subtype,
                        label = label,
                        spacyTypes = listOf(spacyType),
                    ),
                )
//                if(batch.size == BATCH_SIZE) {
//                    wikidataTypeDataPort.saveAll(batch)
//                    logger.info("Saved batch of ${batch.size} types.")
//                    batch.clear()
//                }
            }
        }
        // order batch by id to ensure consistent ordering
        types.sortBy { it.id }
        // find redundant types
        // val uniqueTypes = types.distinctBy { it.id }
        mergeTypes(types)
        // logger.info("Found ${types.size} types, merged to ${mergedTypes.size} unique types.")
        // save all types in one go
        // wikidataTypeDataPort.saveAll(mergedTypes)

//        if(batch.isNotEmpty()) {
//                wikidataTypeDataPort.saveAll(batch)
//                logger.info("Saved batch of ${batch.size} types.")
//                batch.clear()
//            }

        logger.info("Ingestion of types completed in ${(System.currentTimeMillis() - startTime) / 1000.0} seconds.")
    }

    fun mergeTypes(types: List<WikidataType>) {
        types.sortedBy { it.id }
        val distinctIds = types.map { it.id }.distinct()

        for (id in distinctIds) {
            logger.info("Merging types for id: $id")
            val typeGroup = types.filter { it.id == id }
            if (typeGroup.isEmpty()) continue

            val rootTypes = typeGroup.flatMap { it.rootTypes }.distinct()
            val spacyTypes = typeGroup.flatMap { it.spacyTypes }.distinct()
            val label = typeGroup.firstOrNull()?.label ?: ""

//            if(rootTypes.size > 1 || spacyTypes.size > 1) {
//                logger.warn("Merging types with different root types or spacy types for id: $id ($label). " +
//                        "Root types: $rootTypes, Spacy types: $spacyTypes")
//            }
            wikidataTypeDataPort.save(
                WikidataType(
                    id = id,
                    rootTypes = rootTypes,
                    label = label,
                    spacyTypes = spacyTypes,
                ),
            )
        }
    }
}
