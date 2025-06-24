package com.aladigis.spotnik.port.data

import com.aladigis.spotnik.model.LinkedEntity

interface LinkedEntityDataPort {
    fun findByIds(
        ids: List<String>
    ): List<LinkedEntity>
}