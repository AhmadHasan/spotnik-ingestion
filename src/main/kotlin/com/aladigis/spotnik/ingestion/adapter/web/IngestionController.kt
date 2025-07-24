package com.aladigis.spotnik.ingestion.adapter.web

import com.aladigis.spotnik.ingestion.port.IngestionPort
import com.aladigis.spotnik.ingestion.port.WikdataTypeIngestionPort
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class IngestionController {
    @Autowired
    private lateinit var ingestionPort: IngestionPort

    @Autowired
    private lateinit var wikdataTypeIngestionPort: WikdataTypeIngestionPort
    @PostMapping("/ingest")
    fun ingest(
        @RequestParam fromLine: Int = 0,
        @RequestParam toLine: Int = Int.MAX_VALUE,
    ): ResponseEntity<String> {
        //val fileName = "/Users/ahmadhaidar/Downloads/extracted_chunks_bash_bzip2/chunk_0001.json"
        // val fileName = "/Users/ahmadhaidar/Downloads/latest-all.json.bz2"
        //val fileName = "/Users/ahmadhaidar/Downloads/trump.json"

        val fileName = "latest-all.json.bz2"

        try {
            ingestionPort.ingest(fileName, fromLine, toLine)
            return ResponseEntity.ok("Ingestion completed successfully.")
        } catch (e: Exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ingestion failed: ${e.message}")
        }
    }

    @PostMapping("/ingest-types")
    fun ingestTypes(): ResponseEntity<String> {
        val fileName = "/Users/ahmadhaidar/Downloads/wikidata_types/newBegin/all_clean_no_header.tsv"
        try {
            wikdataTypeIngestionPort.ingestSubtypes(fileName)
            return ResponseEntity.ok("Ingestion of types completed successfully.")
        } catch (e: Exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ingestion of types failed: ${e.message}")
        }
    }
}
