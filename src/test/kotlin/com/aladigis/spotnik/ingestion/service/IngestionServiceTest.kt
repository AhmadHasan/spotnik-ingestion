package com.aladigis.spotnik.ingestion.service

import com.aladigis.spotnik.ingestion.model.LinkedEntity
import com.aladigis.spotnik.ingestion.model.LinkedLabel
import com.aladigis.spotnik.ingestion.port.data.LinkedEntityDataPort
import com.aladigis.spotnik.ingestion.port.data.LinkedLabelDataPort
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import java.io.BufferedReader
import java.io.File
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class IngestionServiceTest {
    @Mock
    private lateinit var linkedEntityDataPort: LinkedEntityDataPort

    @Mock
    private lateinit var linkedLabelDataPort: LinkedLabelDataPort

    @InjectMocks
    private lateinit var ingestionService: IngestionService

    private val objectMapper = jacksonObjectMapper()

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun `should ingest file and save entities and labels`() {
        // Arrange
        val testFilePath = "src/test/resources/wikidata1.json"

        val expectedEntity =
            LinkedEntity(
                id = "Q1",
                mainImage = null,
                descriptions = mapOf("en" to "Sample description"),
                instanceOf = listOf("Q2"),
                features = mapOf("P31" to listOf("Q2")),
                wikipediaUrlNames = mapOf("en" to "Sample_Wikipedia_Page"),
                spacyTypes = listOf("Type1", "Type2"),
            )
        val expectedLabels =
            listOf(
                LinkedLabel(entityId = "Q1", value = "Sample Label", language = "en", main = true),
            )

        // Act
        ingestionService.ingest(testFilePath)
    }

    @Test
    fun findTopTypes(){
        val workingDirectory = "/Users/ahmadhaidar/Downloads/wikidata_types/newBegin"
        val currentRootsFilePath = "$workingDirectory/all_clean.tsv"
        val roots = listOf(
            "Q5       ",
                    "Q43229   ",
                    "Q783794  ",
                    "Q163740  ",
                    "Q7278    ",
                    "Q6256    ",
                    "Q515     ",
                    "Q3957    ",
                    "Q56061   ",
                    "Q1182803 ",
                    "Q82794   ",
                    "Q2221906 ",
                    "Q23442   ",
                    "Q8502    ",
                    "Q8504    ",
                    "Q8436    ",
                    "Q9174    ",
                    "Q7278    ",
                    "Q28108   ",
                    "Q13226383",
                    "Q1248784 ",
                    "Q62447   ",
                    "Q1248784 ",
                    "Q811979  ",
                    "Q2424752 ",
                    "Q11446   ",
                    "Q7397    ",
                    "Q1047215 ",
                    "Q1190554 ",
                    "Q180684  ",
                    "Q198     ",
                    "Q1656682",
                    "Q838948  ",
                    "Q13593966",
                    "Q7725634 ",
                    "Q386724  ",
                    "Q820655  ",
                    "Q2334719",
                    "Q34770   ").map { it.trim() }

        // read lines from the file at currentRootsFilePath
        val lines = File(currentRootsFilePath).readLines()
        val types = lines
            .drop(1) // Skip header
            .map { it.split("\t") }
            .map { Triple(it[0], it[1] , it[2])} // Pair of (type, parent)

        val firstLevel = types.filter {
            it.second in roots
        }

        val secondLevel = types.filter {
            it.first in roots && it.second !in roots
        }

        val thirdLevel = types.filter {
            it.first !in roots
        }

        // write first and level into a file at workingDirectory/newRoots.tsv
        val newRootsFilePath = "$workingDirectory/newRoots.tsv"
        File(newRootsFilePath).bufferedWriter().use { writer ->
            firstLevel.forEach { writer.write("${it.first}\t${it.second}\t${it.third}\n") }
            secondLevel.forEach { writer.write("${it.first}\t${it.second}\t${it.third}\n") }
        }

        println("NotTopRoots: ${thirdLevel.size}")
        thirdLevel.forEach { println("wd:${it.second}") }
        println("Second Level Types: ${secondLevel.size}")
    }
}
