package com.aladigis.spotnik.adapter.ner

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.WebClient

@Configuration
class NERWebClientConfig {
    @Bean
    fun nerWebClient(): WebClient {
        return WebClient.builder().build()
    }
}
