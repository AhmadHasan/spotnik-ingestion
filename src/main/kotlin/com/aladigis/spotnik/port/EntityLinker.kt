package com.aladigis.spotnik.port

import com.aladigis.spotnik.port.dto.LinkingResult

interface EntityLinker {
    fun link(
        language: String,
        labels: List<String>,
    ): LinkingResult
}
