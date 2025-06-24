package com.aladigis.spotnik.adapter.ner

data class NEREntityDto(
    val text: String,
    val label: String,
    val language: String,
    val startChar: Int,
    val endChar: Int
)
