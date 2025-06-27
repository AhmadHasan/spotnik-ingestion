package com.aladigis.spotnik.adapter.ner

import com.fasterxml.jackson.annotation.JsonProperty

data class NEREntityDto(
    val text: String,
    val label: String,
    val language: String,
    @JsonProperty("start_char")
    val startChar: Int,
    @JsonProperty("end_char")
    val endChar: Int,
)
