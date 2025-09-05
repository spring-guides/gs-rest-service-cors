package com.example.restservicecors

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@SpringBootApplication
class RestServiceCorsApplication {

    @Bean
    fun corsConfigurer(): WebMvcConfigurer = object : WebMvcConfigurer {
        override fun addCorsMappings(registry: CorsRegistry) {
            registry.addMapping("/greeting-javaconfig").allowedOrigins("http://localhost:9000")
        }
    }
}

fun main(args: Array<String>) {
    runApplication<RestServiceCorsApplication>(*args)
}
