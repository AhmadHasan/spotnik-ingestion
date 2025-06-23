package com.aladigis.spotnik.model

import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "entities")
data class LinkedEntity(
    val id: String,
    val qid: String,
    val descriptions: Map<String, String>,
    val instanceOf: List<String>,
)
