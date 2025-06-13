package com.aladigis.spotnik.port

import com.aladigis.spotnik.model.SpottedEntity

interface SpotPort {
    fun spot(text: String): List<SpottedEntity>
    }