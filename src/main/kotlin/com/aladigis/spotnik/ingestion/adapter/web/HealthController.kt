package com.aladigis.spotnik.ingestion.adapter.web

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/health")
class HealthController {
    @GetMapping
    fun healthCheck(): ResponseEntity<Map<String, String>> {
        return ResponseEntity.ok(
            mapOf(
                "status" to "UP",
                "service" to "running",
            ),
        )
    }
}
