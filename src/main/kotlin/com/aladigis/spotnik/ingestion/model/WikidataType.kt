package com.aladigis.spotnik.ingestion.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "types")
data class WikidataType(
    @Id
    val id: String,
    val rootTypes: List<String>,
    val label: String?,
    val spacyTypes: List<String>,
)
