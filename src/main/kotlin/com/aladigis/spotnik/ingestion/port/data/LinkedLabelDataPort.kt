package com.aladigis.spotnik.ingestion.port.data

import com.aladigis.spotnik.ingestion.model.LinkedLabel

interface LinkedLabelDataPort {
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

    fun saveAll(
        linkedLabels: List<LinkedLabel>,
    ): List<LinkedLabel>
}
