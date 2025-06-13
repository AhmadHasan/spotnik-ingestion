package com.aladigis.spotnik.port.dto

import com.aladigis.spotnik.adapter.ner.NEREntity

data class NERResponseDto(
    val success: Boolean,
    val language: String,
    val entities: List<NEREntity>
)