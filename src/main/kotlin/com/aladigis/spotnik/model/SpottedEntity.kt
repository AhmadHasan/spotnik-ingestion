package com.aladigis.spotnik.model

data class SpottedEntity(
    val label: String,
    val start: Int,
    val end: Int,
    val wikipediaUrl: String? = null,
    val wikidataUrl: String? = null,
    val description: String? = null,
    val imageUrl: String? = null,
)
