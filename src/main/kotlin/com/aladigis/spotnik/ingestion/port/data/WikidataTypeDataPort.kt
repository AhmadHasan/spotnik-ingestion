package com.aladigis.spotnik.ingestion.port.data

import com.aladigis.spotnik.ingestion.model.WikidataType

interface WikidataTypeDataPort {
    fun save(wikidataType: WikidataType)

    fun saveAll(wikidataTypes: List<WikidataType>)

    fun findById(id: String): WikidataType?
    fun findByIdIn(typeIds: List<String>): List<WikidataType>
}