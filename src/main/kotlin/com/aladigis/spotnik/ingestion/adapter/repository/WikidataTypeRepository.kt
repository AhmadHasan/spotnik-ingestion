package com.aladigis.spotnik.ingestion.adapter.repository

import com.aladigis.spotnik.ingestion.model.WikidataType
import org.springframework.data.mongodb.repository.MongoRepository

interface WikidataTypeRepository : MongoRepository<WikidataType, String>
