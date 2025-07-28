package com.aladigis.spotnik.ingestion.service.component

import com.aladigis.spotnik.ingestion.config.NerConfig
import com.aladigis.spotnik.ingestion.port.data.WikidataTypeDataPort
import org.mockito.InjectMocks
import org.mockito.Mock

// import org.junit.jupiter.api.BeforeEach
// import org.junit.jupiter.api.Test
// import org.mockito.ArgumentMatchers.anyList

class LinkedEntityProcessorTest {
    @Mock
    private lateinit var nerConfig: NerConfig

    @Mock
    private lateinit var wikidataTypeDataPort: WikidataTypeDataPort

    @InjectMocks
    private val processor = LinkedEntityProcessor()

//    @BeforeEach
//    fun setUp() {
//        whenever(nerConfig.getSpacyTypes(anyList()))
//            .thenReturn(listOf("Person", "Organization", "Location"))
//    }
//
//    @Test
//    fun `should parse entity correctly`() {
//        // load file wikidata1
//        val rawItem =
//            javaClass.getResourceAsStream("/wikidata1.json")?.bufferedReader()?.use { it.readText() }?.let {
//                com.fasterxml.jackson.databind
//                    .ObjectMapper()
//                    .readTree(it)
//            } ?: throw IllegalArgumentException("Resource not found: wikidata1.json")
//        val entity = processor.parseEntity(rawItem)
//        assert(entity != null) { "Entity should not be null" }
//    }
}
