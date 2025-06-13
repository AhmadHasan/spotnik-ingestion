package com.aladigis.spotnik.adapter.web.dto

data class SpotEntityDto (
    val start: Int,
    val end: Int,
    val label: String,
    val wikipediaUrl: String? = null,
    val wikidataUrl: String? = null,
    val description: String? = null,
    val imageUrl: String? = null,
)
