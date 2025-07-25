package com.aladigis.spotnik.ingestion.adapter.web

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/health")
class HealthController {
    @Autowired
    private lateinit var spotnikIngestionConfiguration: com.aladigis.spotnik.ingestion.config.SpotnikIngestionConfiguration

    @GetMapping
    fun healthCheck(): ResponseEntity<Map<String, String>> {
        return ResponseEntity.ok(
            mapOf(
                "status" to "UP",
                "service" to "running",
            ),
        )
    }

    @GetMapping("/config")
    fun getConfig(): ResponseEntity<Map<String, Any>> {
        return ResponseEntity.ok(
            mapOf(
                "batchSize" to spotnikIngestionConfiguration.batchSize,
                "ingestableTypes" to spotnikIngestionConfiguration.ingestableTypes,
            ),
        )
    }
}
