package com.aladigis.spotnik.port

import com.aladigis.spotnik.model.SpottedEntity

interface EntityLinker {
    fun link(entityLabel: String): SpottedEntity?
}