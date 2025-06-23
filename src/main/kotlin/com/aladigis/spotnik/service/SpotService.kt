package com.aladigis.spotnik.service

import com.aladigis.spotnik.model.SpottedEntity
import com.aladigis.spotnik.port.Disambiguator
import com.aladigis.spotnik.port.EntityLinker
import com.aladigis.spotnik.port.NERPort
import com.aladigis.spotnik.port.SpotPort
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class SpotService : SpotPort {
    @Autowired
    private lateinit var nerPort: NERPort

    @Autowired
    private lateinit var entityLinker: EntityLinker

    @Autowired
    private lateinit var disambiguator: Disambiguator

    override fun spot(text: String): List<SpottedEntity> {
        val entities = nerPort.extractEntities(text)
        val labels =
            entities
                .entities
                .map { it.text }
                .distinct()

        val linkingResults = entityLinker.link(entities.language, labels)
        val disambiguated = disambiguator.disambiguate(linkingResults, entities.language)

        return entities.entities.map { entity ->
            SpottedEntity(
                label = entity.text,
                start = entity.startChar,
                end = entity.endChar,
                entityId =
                    disambiguated.labelEntities
                        .find { it.label.equals(entity.text, ignoreCase = true) }
                        ?.entityId ?: "",
            )
        }
    }
}
