package com.aladigis.spotnik.port

import com.aladigis.spotnik.port.dto.NERResponseDto

interface NERPort {
    fun extractEntities(text: String): NERResponseDto
}
