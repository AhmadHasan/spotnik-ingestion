package com.aladigis.spotnik.ingestion.adapter.web

import com.aladigis.spotnik.ingestion.port.IngestionPort
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class IngestionController {

    @Autowired
    private lateinit var ingestionPort: IngestionPort

    @PostMapping("/ingest")
    fun ingest(): ResponseEntity<String> {
        val chunk0 = "/Users/ahmadhaidar/Downloads/extracted_chunks_bash_bzip2/chunk_0001.json"
        val allbz2 =  "/Users/ahmadhaidar/Downloads/latest-all.json.bz2"
        val fileName = allbz2
        
        try {
            ingestionPort.ingest(fileName)
            return ResponseEntity.ok("Ingestion completed successfully.")
        } catch (e: Exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ingestion failed: ${e.message}")
        }
    }
}