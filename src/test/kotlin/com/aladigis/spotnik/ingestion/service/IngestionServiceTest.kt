package com.aladigis.spotnik.ingestion.service

import com.aladigis.spotnik.ingestion.model.LinkedEntity
import com.aladigis.spotnik.ingestion.model.LinkedLabel
import com.aladigis.spotnik.ingestion.port.data.LinkedEntityDataPort
import com.aladigis.spotnik.ingestion.port.data.LinkedLabelDataPort
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
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
            )
        val expectedLabels =
            listOf(
                LinkedLabel(entityId = "Q1", value = "Sample Label", language = "en", main = true),
            )

        // Act
        ingestionService.ingest(testFilePath)
    }
}
