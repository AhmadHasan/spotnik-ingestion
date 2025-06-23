package com.aladigis.spotnik.adapter.repository

import com.aladigis.spotnik.model.LinkedLabel
import com.aladigis.spotnik.port.data.LinkedLabelDataPort
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class LinkedLabelDataService : LinkedLabelDataPort {
    @Autowired
    private lateinit var linkedLabelRepository: LinkedLabelRepository

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
        return linkedLabelRepository.findByValueInAndLanguage(values, language)
    }
}
