package com.aladigis.spotnik.port.dto

import com.aladigis.spotnik.model.LinkedEntity

data class DisambiguatedLabel(
    val label: String,
    val entity: LinkedEntity,
)
