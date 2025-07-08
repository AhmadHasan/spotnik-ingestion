package com.aladigis.spotnik.ingestion.adapter.repository

import com.aladigis.spotnik.ingestion.model.LinkedEntity
import org.springframework.data.mongodb.repository.MongoRepository

interface LinkedEntityRepository : MongoRepository<LinkedEntity, String>
