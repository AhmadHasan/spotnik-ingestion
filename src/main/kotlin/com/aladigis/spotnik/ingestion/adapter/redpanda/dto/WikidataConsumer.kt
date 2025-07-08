package com.aladigis.spotnik.ingestion.adapter.redpanda.dto

import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class WikidataConsumer {

    @KafkaListener(topics = ["wikidata-ingestion"], groupId = "wikidata-group")
    fun consume(message: WikidataIngestionBatch) {
        println("Received block with ${message.wikidataLines.size} lines")
    }
}