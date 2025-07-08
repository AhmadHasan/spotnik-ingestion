package com.aladigis.spotnik.ingestion.adapter.web.dto

data class SpotResponseDto(
    val text: String,
    val entities: List<SpotEntityDto>,
)
