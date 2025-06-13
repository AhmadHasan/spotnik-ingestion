package com.aladigis.spotnik.adapter.ner

data class NEREntity(
    val text: String,
    val label: String,
    val language: String,
    val startChar: Int,
    val endChar: Int,
)
