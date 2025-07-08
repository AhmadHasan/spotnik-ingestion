package com.aladigis.spotnik.ingestion

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SpotnikApplication

fun main(args: Array<String>) {
    runApplication<com.aladigis.spotnik.ingestion.SpotnikApplication>(*args)
}
