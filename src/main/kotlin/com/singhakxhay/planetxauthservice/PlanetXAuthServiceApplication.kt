package com.singhakxhay.planetxauthservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity

@SpringBootApplication
@EnableWebSecurity
class PlanetXAuthServiceApplication

fun main(args: Array<String>) {
	runApplication<PlanetXAuthServiceApplication>(*args)
}
