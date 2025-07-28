package com.aladigis.spotnik.ingestion.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class WebController {
    @GetMapping("/test")
    fun showTestPage(): String = "ner"
}
