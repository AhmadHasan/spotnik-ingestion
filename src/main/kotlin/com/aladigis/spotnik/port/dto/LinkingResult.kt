package com.aladigis.spotnik.port.dto

open class LinkingResult(
    val text: String,
    val labelEntities: Map<String, List<String>>,
)
