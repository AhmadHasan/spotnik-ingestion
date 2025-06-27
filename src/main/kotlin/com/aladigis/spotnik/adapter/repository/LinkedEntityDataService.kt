package com.aladigis.spotnik.adapter.repository

import com.aladigis.spotnik.model.LinkedEntity
import com.aladigis.spotnik.port.data.LinkedEntityDataPort
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class LinkedEntityDataService : LinkedEntityDataPort {
    @Autowired
    private lateinit var linkedEntityRepository: LinkedEntityRepotirory

    override fun findByIds(ids: List<String>): List<LinkedEntity> {
        return linkedEntityRepository.findAllById(ids)
    }
}
