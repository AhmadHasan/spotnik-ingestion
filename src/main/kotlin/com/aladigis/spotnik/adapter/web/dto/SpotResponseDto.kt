package com.aladigis.spotnik.adapter.web.dto

data class SpotResponseDto(
    val text: String,
    val entities: List<SpotEntityDto>
)
