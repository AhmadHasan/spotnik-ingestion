package com.aladigis.spotnik.ingestion.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "entities")
data class LinkedEntity(
    @Id
    val id: String,
    val mainImage: String?,
    val descriptions: Map<String, String>,
    val instanceOf: List<String>,
    val features: Map<String, List<String>>,
    val spacyTypes: List<String>,
    val wikipediaUrlNames: Map<String, String>,
)
