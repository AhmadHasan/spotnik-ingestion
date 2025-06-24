package com.aladigis.spotnik.port.dto

import com.aladigis.spotnik.adapter.ner.NEREntityDto

data class NERResponseDto(
    val success: Boolean,
    val language: String,
    val entities: List<NEREntityDto>,
)
