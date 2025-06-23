package com.aladigis.spotnik.service

import com.aladigis.spotnik.port.EntityLinker
import com.aladigis.spotnik.port.data.LinkedLabelDataPort
import com.aladigis.spotnik.port.dto.LinkingResult
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class LinkingService : EntityLinker {
    @Autowired
    private lateinit var linkedLabelDataPort: LinkedLabelDataPort

    override fun link(
        language: String,
        labels: List<String>,
    ): LinkingResult {
        val links = linkedLabelDataPort.findByValueInAndLanguage(labels, language)
        // map each label to all entities  that match it
        val labelEntities =
            links.groupBy { it.value.lowercase() }
                .mapValues { (_, linkedLabels) ->
                    linkedLabels.map { it.entityId }
                }
        return LinkingResult(
            text = labels.joinToString(", "),
            labelEntities = labelEntities,
        )
    }
}
