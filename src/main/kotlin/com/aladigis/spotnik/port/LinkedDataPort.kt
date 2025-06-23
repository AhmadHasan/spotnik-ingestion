package com.aladigis.spotnik.port

import com.aladigis.spotnik.model.LinkedLabel

interface LinkedDataPort {
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
}
