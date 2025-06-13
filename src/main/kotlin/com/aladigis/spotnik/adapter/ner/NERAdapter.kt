package com.aladigis.spotnik.adapter.ner

import com.aladigis.spotnik.port.NERPort

import com.aladigis.spotnik.port.dto.NERResponseDto
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient

@Component
class NERAdapter : NERPort {

    @Autowired
    private lateinit var nerServiceConfig: NERServiceConfig

    @Autowired
    private lateinit var nerWebClient: WebClient

    private val logger = LoggerFactory.getLogger(NERAdapter::class.java)


    override fun extractEntities(text: String): NERResponseDto {
        val url = "${nerServiceConfig.protocol}://${nerServiceConfig.host}:${nerServiceConfig.port}/${nerServiceConfig.endpoints["extractEntities"] ?: "extractEntities"}"
        logger.info("Calling NER Service at URL: $url")

        val startTime = System.currentTimeMillis()

        val response = nerWebClient.post()
            .uri(url)
            .header("Content-Type", "application/json")
            .bodyValue(mapOf("text" to text))
            .retrieve()
            .bodyToMono(NERResponseDto::class.java)
            .block()

        val endTime = System.currentTimeMillis()
        val responseTime = endTime - startTime

        if (response != null) {
            val responsePreview = response.toString().take(100)
            logger.info("Response time: ${responseTime}ms, Response preview: $responsePreview")
            return response
        } else {
            throw RuntimeException("Failed to extract entities from NER Service")
        }
    }
}