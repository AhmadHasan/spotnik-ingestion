package com.aladigis.spotnik.adapter.ner

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix = "application.nerservice")
class NERServiceConfig {
    lateinit var protocol: String
    lateinit var host: String
    lateinit var port: String
    lateinit var endpoints: Map<String, String>
}
