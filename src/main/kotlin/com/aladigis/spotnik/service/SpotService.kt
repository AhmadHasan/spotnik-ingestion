package com.aladigis.spotnik.service

import com.aladigis.spotnik.model.SpottedEntity
import com.aladigis.spotnik.port.NERPort
import com.aladigis.spotnik.port.SpotPort
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class SpotService: SpotPort {

    @Autowired
    lateinit var nerPort: NERPort
    override fun spot(text: String): List<SpottedEntity> {
        // get NERs from NER Service
        val entities = nerPort.extractEntities(text)
        // Link the entities
        // Disambiguate the entities
        return entities.entities.map { entity ->
            SpottedEntity(
                label = entity.text,
                start = entity.startChar,
                end = entity.endChar,
            ) }
    }
}