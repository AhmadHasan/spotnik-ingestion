package com.aladigis.spotnik.adapter.repository

import com.aladigis.spotnik.model.LinkedLabel
import org.springframework.data.mongodb.repository.MongoRepository

interface LinkedLabelRepository : MongoRepository<LinkedLabel, String> {
    fun findByEntityId(entityId: String): List<LinkedLabel>

    fun findByValueAndLanguage(
        value: String,
        language: String,
    ): LinkedLabel?

    fun findByValueContainingIgnoreCaseAndLanguage(
        value: String,
        language: String,
    ): List<LinkedLabel>

    fun findByMainTrue(): List<LinkedLabel>

    fun findByValueInAndLanguage(
        values: List<String>,
        language: String,
    ): List<LinkedLabel>
}
