package com.aladigis.spotnik.ingestion.model

import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "labels")
data class LinkedLabel(
    val entityId: String,
    val value: String,
    val language: String,
    val main: Boolean = false,
)
