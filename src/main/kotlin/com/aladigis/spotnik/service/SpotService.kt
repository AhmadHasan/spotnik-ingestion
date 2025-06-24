package com.aladigis.spotnik.service

import com.aladigis.spotnik.model.SpottedEntity
import com.aladigis.spotnik.port.Disambiguator
import com.aladigis.spotnik.port.EntityLinker
import com.aladigis.spotnik.port.NERPort
import com.aladigis.spotnik.port.SpotPort
import com.aladigis.spotnik.port.dto.DisambiguationResult
import com.aladigis.spotnik.port.dto.LinkingResult
import com.aladigis.spotnik.port.dto.NERResponseDto
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

    @Autowired
    private lateinit var urlGenerator: URLGenerator

    override fun spot(text: String): List<SpottedEntity> {
        val nerEntities = nerPort.extractEntities(text)
        val labels =
            nerEntities
                .entities
                .map { it.text }
                .distinct()

        val linkingResults = entityLinker.link(nerEntities.language, labels)
        val disambiguated = disambiguator.disambiguate(linkingResults, nerEntities.language)
        val spottedEntities = mergeResults(nerEntities, linkingResults, disambiguated)

        return spottedEntities
    }

    private fun mergeResults(nerEntities: NERResponseDto, linkingResults: LinkingResult, disambiguated: DisambiguationResult): List<SpottedEntity> {
        return nerEntities.entities.map { entity ->
            SpottedEntity(
                label = entity.text,
                start = entity.startChar,
                end = entity.endChar,
                entityId =
                disambiguated.labelEntities
                    .find { it.label.equals(entity.text, ignoreCase = true) }
                    ?.entity?.id?: "",
                wikipediaUrl =
                urlGenerator.generateEntityWikipediaUrl(
                    nerEntities.language,
                    entity.text,
                ),
                imageUrl =
                urlGenerator.generateEntityImageUrl(
                    disambiguated.labelEntities.find { it.label.equals(entity.text, ignoreCase = true) }
                        ?.entity?.mainImage?:""
                ),
            )
        }

    }
}
