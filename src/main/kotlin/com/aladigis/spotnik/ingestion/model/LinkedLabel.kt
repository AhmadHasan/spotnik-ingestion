package com.aladigis.spotnik.ingestion.model

import org.springframework.data.mongodb.core.index.CompoundIndex
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "labels")
@CompoundIndex(name = "value_language_idx", def = "{'value': 1, 'language': 1}")
data class LinkedLabel(
    @Indexed
    val entityId: String,
    @Indexed
    val value: String,
    val language: String,
    val main: Boolean = false,
)
