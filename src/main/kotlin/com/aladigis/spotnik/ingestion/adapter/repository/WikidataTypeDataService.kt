package com.aladigis.spotnik.ingestion.adapter.repository

import com.aladigis.spotnik.ingestion.model.WikidataType
import com.aladigis.spotnik.ingestion.port.data.WikidataTypeDataPort
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class WikidataTypeDataService: WikidataTypeDataPort {

    @Autowired
    private lateinit var wikidataTypeRepository: WikidataTypeRepository

    override fun save(wikidataType: WikidataType) {
        wikidataTypeRepository.save(wikidataType)
    }

    override fun saveAll(wikidataTypes: List<WikidataType>) {
        wikidataTypeRepository.saveAll(wikidataTypes)
    }

    override fun findById(id: String): WikidataType? {
        return wikidataTypeRepository.findById(id).orElse(null)
    }

    override fun findByIdIn(typeIds: List<String>): List<WikidataType> {
        return wikidataTypeRepository.findAllById(typeIds)
    }
}