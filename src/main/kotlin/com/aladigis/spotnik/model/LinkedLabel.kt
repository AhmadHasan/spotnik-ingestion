package com.aladigis.spotnik.model

import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "labels")
data class LinkedLabel(
    val id: String,
    val entityId: String,
    val value: String,
    val language: String,
    val main: Boolean,
)
