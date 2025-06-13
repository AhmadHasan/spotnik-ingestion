package com.aladigis.spotnik

import com.aladigis.spotnik.model.SpottedEntity
import org.springframework.data.mongodb.repository.MongoRepository

interface SpottedEntityRepository : MongoRepository<SpottedEntity, String>
