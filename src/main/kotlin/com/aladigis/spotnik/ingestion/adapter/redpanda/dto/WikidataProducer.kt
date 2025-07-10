package com.aladigis.spotnik.ingestion.adapter.redpanda.dto

import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service

@Service
class WikidataProducer(private val kafkaTemplate: KafkaTemplate<String, WikidataIngestionBatch>) {
    fun send(block: WikidataIngestionBatch) {
        kafkaTemplate.send("wikidata-ingestion", block)
    }
}
