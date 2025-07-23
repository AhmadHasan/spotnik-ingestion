package com.aladigis.spotnik.ingestion.adapter.repository

import com.aladigis.spotnik.ingestion.model.LinkedLabel
import com.aladigis.spotnik.ingestion.port.data.LinkedLabelDataPort
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class LinkedLabelDataService : LinkedLabelDataPort {
    @Autowired
    private lateinit var linkedLabelRepository: LinkedLabelRepository

    private val logger = LoggerFactory.getLogger(LinkedLabelDataService::class.java)

    override fun findByEntityId(entityId: String): List<LinkedLabel> {
        return linkedLabelRepository.findByEntityId(entityId)
    }

    override fun findByValueAndLanguage(
        value: String,
        language: String,
    ): LinkedLabel? {
        return linkedLabelRepository.findByValueAndLanguage(value, language)
    }

    override fun findByValueContainingIgnoreCaseAndLanguage(
        value: String,
        language: String,
    ): List<LinkedLabel> {
        return linkedLabelRepository.findByValueContainingIgnoreCaseAndLanguage(value, language)
    }

    override fun findByMainTrue(): List<LinkedLabel> {
        return linkedLabelRepository.findByMainTrue()
    }

    override fun findByValueInAndLanguage(
        values: List<String>,
        language: String,
    ): List<LinkedLabel> {
        val startTime = System.currentTimeMillis()
        val result = linkedLabelRepository.findByValueInAndLanguage(values, language)
        logger.info("Repository call findByValueInAndLanguage ${values.size} values took ${System.currentTimeMillis() - startTime} ms")
        return result
    }

    override fun saveAll(linkedLabels: List<LinkedLabel>): List<LinkedLabel> {
        val startTime = System.currentTimeMillis()
        val result = linkedLabelRepository.saveAll(linkedLabels)
        //logger.info("Repository call saveAll with ${linkedLabels.size} labels took ${System.currentTimeMillis() - startTime} ms")
        return result
    }
}
