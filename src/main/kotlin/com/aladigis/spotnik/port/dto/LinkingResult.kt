package com.aladigis.spotnik.port.dto

import com.aladigis.spotnik.model.LinkedEntity

open class LinkingResult(
    val text: String,
    val labelEntityIds: Map<String, List<LinkedEntity>>,
)
