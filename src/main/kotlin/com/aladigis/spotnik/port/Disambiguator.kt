package com.aladigis.spotnik.port

import com.aladigis.spotnik.port.dto.DisambiguationResult
import com.aladigis.spotnik.port.dto.LinkingResult

interface Disambiguator {
    fun disambiguate(
        linkingResult: LinkingResult,
        language: String,
    ): DisambiguationResult
}
