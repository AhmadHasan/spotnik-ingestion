package com.aladigis.spotnik.ingestion.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "spotnik.ner")
data class NerConfig(
    var wikidataTypeMapping: Map<String, List<String>> = emptyMap(),
) {
    fun getSpacyType(wikimediaType: String): String {
        val keys =
            wikidataTypeMapping.filter { it.value.contains(wikimediaType) }
                .keys
        if (keys.isEmpty()) {
            throw IllegalArgumentException(
                "Wikimedia type '$wikimediaType' is not mapped to a single Spacy type. " +
                    "Found keys: $keys",
            )
        }
        if (keys.size > 1) {
            throw IllegalArgumentException(
                "Wikimedia type '$wikimediaType' is mapped to multiple Spacy types: $keys. " +
                    "Please ensure that each Wikimedia type maps to a single Spacy type.",
            )
        }
        return keys.first()
    }

    fun getSpacyTypes(wikimediaTypes: List<String>): List<String> {
        val spacyTypes =
            wikimediaTypes.map { wikimediaType ->
                wikidataTypeMapping.filter { it.value.contains(wikimediaType) }
                    .keys
            }
                .flatten()
                .distinct()
        return spacyTypes
    }
}
