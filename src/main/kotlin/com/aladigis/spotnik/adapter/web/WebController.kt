package com.aladigis.spotnik.controller

import com.aladigis.spotnik.adapter.ner.NERAdapter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class WebController {
    @Autowired
    private lateinit var nerAdapter: NERAdapter

    @GetMapping("/test")
    fun showTestPage(): String {
        return "ner"
    }
}
