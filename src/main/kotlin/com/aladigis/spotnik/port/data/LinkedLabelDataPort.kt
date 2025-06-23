package com.aladigis.spotnik.port.data

import com.aladigis.spotnik.model.LinkedLabel

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
}
