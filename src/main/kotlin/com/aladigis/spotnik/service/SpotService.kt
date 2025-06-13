package com.aladigis.spotnik.service

import com.aladigis.spotnik.model.SpottedEntity
import com.aladigis.spotnik.port.NERPort
import com.aladigis.spotnik.port.SpotPort
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class SpotService : SpotPort {
    @Autowired
    lateinit var nerPort: NERPort

    override fun spot(text: String): List<SpottedEntity> {
        val entities = nerPort.extractEntities(text)
        return entities.entities.map { entity ->
            SpottedEntity(
                label = entity.text,
                start = entity.startChar,
                end = entity.endChar,
            )
        }
    }
}
