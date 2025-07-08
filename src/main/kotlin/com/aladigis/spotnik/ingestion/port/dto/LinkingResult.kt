package com.aladigis.spotnik.ingestion.port.dto

import com.aladigis.spotnik.ingestion.model.LinkedEntity

open class LinkingResult(
    val text: String,
    val labelEntityIds: Map<String, List<LinkedEntity>>,
)
