package com.aladigis.spotnik.ingestion

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableAsync

@SpringBootApplication
@EnableAsync
class SpotnikApplication

fun main(args: Array<String>) {
    runApplication<SpotnikApplication>(*args)
}
