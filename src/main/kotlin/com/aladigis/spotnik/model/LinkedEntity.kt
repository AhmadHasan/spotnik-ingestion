package com.aladigis.spotnik.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "entities")
data class LinkedEntity(
    @Id
    val id: String,
    val qid: String,
    val mainImage: String,
    val descriptions: Map<String, String>,
    val instanceOf: List<String>,
)
