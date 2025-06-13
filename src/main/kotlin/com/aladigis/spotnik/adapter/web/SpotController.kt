package com.aladigis.spotnik.adapter.web

import com.aladigis.spotnik.adapter.web.dto.SpotEntityDto
import com.aladigis.spotnik.adapter.web.dto.SpotRequestDto
import com.aladigis.spotnik.adapter.web.dto.SpotResponseDto
import com.aladigis.spotnik.port.SpotPort
import com.aladigis.spotnik.service.SpotService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/spot")
class SpotController {

    @Autowired
    lateinit var spotPort: SpotPort // Assuming you have a SpotService to handle the logic
    @PostMapping
    fun spot(@RequestBody request: SpotRequestDto): ResponseEntity<SpotResponseDto> {
        val result = spotPort.spot(
            request.text
        )
        val response = SpotResponseDto(
            text = request.text,
            entities = result.map{
                entity ->
                // Assuming you have a method to convert SpottedEntity to SpotEntityDto
                SpotEntityDto(
                    start = entity.start,
                    end = entity.end,
                    label = entity.label,
                    wikipediaUrl = null, // Replace with actual URL if available
                    wikidataUrl = null, // Replace with actual URL if available
                    description = null, // Replace with actual description if available
                    imageUrl = null // Replace with actual image URL if available
                )
            } // Replace with actual entity processing
        )
        return ResponseEntity.ok(response)
    }
}
