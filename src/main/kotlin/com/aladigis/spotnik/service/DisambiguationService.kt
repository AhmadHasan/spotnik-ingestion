package com.aladigis.spotnik.service

import com.aladigis.spotnik.port.Disambiguator
import com.aladigis.spotnik.port.dto.DisambiguatedLabel
import com.aladigis.spotnik.port.dto.DisambiguationResult
import com.aladigis.spotnik.port.dto.LinkingResult
import org.springframework.stereotype.Service

@Service
class DisambiguationService : Disambiguator {
    override fun disambiguate(
        linkingResult: LinkingResult,
        language: String,
    ): DisambiguationResult {
        return DisambiguationResult(
            linkingResult.labelEntityIds.map {
                    labelEntity ->
                DisambiguatedLabel(labelEntity.key, labelEntity.value.first())
            },
        )
    }
}
