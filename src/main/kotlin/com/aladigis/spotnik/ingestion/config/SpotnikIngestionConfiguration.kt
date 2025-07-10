package com.aladigis.spotnik.ingestion.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "spotnik.ingestion")
class SpotnikIngestionConfiguration {
    var batchSize: Int = 0
}
