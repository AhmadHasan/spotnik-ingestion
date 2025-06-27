package com.aladigis.spotnik.adapter.repository

import com.aladigis.spotnik.model.LinkedEntity
import org.springframework.data.mongodb.repository.MongoRepository

interface LinkedEntityRepotirory : MongoRepository<LinkedEntity, String>
