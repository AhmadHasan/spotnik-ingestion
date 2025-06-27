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

    @Autowired
    private lateinit var linkedEntityDataPort: com.aladigis.spotnik.port.data.LinkedEntityDataPort

    override fun link(
        language: String,
        labels: List<String>,
    ): LinkingResult {
        val labels = linkedLabelDataPort.findByValueInAndLanguage(labels, language)
        val entityIds = labels.map { it.entityId }
        val entities = linkedEntityDataPort.findByIds(entityIds)

        val labelEntityIds =
            labels
                .groupBy { it.value }
                .mapValues { entry ->
                    entry.value.map { label ->
                        entities.find { it.id == label.entityId }
                    }.filterNotNull()
                }
        return LinkingResult(
            text = labels.joinToString(", ") { it.value },
            labelEntityIds = labelEntityIds,
        )
    }
}
