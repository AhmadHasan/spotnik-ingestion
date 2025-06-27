package com.aladigis.spotnik.model

data class SpottedEntity(
    val label: String,
    val start: Int,
    val end: Int,
    val entityId: String,
    val wikipediaUrl: String? = null,
    val wikidataUrl: String? = null,
    val description: String = "",
    val imageUrl: String? = null,
)
