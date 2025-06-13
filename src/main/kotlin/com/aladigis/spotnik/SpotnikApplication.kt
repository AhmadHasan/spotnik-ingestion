package com.aladigis.spotnik

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SpotnikApplication

fun main(args: Array<String>) {
    runApplication<SpotnikApplication>(*args)
}
