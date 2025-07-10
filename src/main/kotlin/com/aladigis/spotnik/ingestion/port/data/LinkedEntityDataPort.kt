package com.aladigis.spotnik.ingestion.port.data

import com.aladigis.spotnik.ingestion.model.LinkedEntity

interface LinkedEntityDataPort {
    fun findByIds(ids: List<String>): List<LinkedEntity>

    fun saveAll(entities: List<LinkedEntity>): List<LinkedEntity>
}
