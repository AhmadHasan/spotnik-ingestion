package com.aladigis.spotnik.adapter.web

import com.aladigis.spotnik.adapter.web.dto.SpotEntityDto
import com.aladigis.spotnik.adapter.web.dto.SpotRequestDto
import com.aladigis.spotnik.adapter.web.dto.SpotResponseDto
import com.aladigis.spotnik.port.SpotPort
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/spot")
class SpotController {
    @Autowired
    lateinit var spotPort: SpotPort

    @PostMapping
    fun spot(
        @RequestBody request: SpotRequestDto,
    ): ResponseEntity<SpotResponseDto> {
        val result =
            spotPort.spot(
                request.text,
            )
        val response =
            SpotResponseDto(
                text = request.text,
                entities =
                    result.map {
                            entity ->
                        SpotEntityDto(
                            start = entity.start,
                            end = entity.end,
                            label = entity.label,
                            wikipediaUrl = null,
                            wikidataUrl = null,
                            description = null,
                            imageUrl = null,
                        )
                    },
            )
        return ResponseEntity.ok(response)
    }
}
