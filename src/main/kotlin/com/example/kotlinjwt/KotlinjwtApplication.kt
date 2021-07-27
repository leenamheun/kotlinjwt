package com.example.kotlinjwt

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.web.reactive.config.EnableWebFlux


@EnableAsync
@EnableWebFlux
@EnableJpaAuditing
@ConfigurationPropertiesScan("com.example.kotlinjwt.config")
@SpringBootApplication
class KotlinjwtApplication

fun main(args: Array<String>) {
    runApplication<KotlinjwtApplication>(*args)
}
