package com.example.restservicecors

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.RequestEntity
import java.net.URI

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class GreetingIntegrationTests @Autowired constructor(
    private val restTemplate: TestRestTemplate
) {

    @Test
    fun `cors with annotation`() {
        val entity = restTemplate.exchange(
            RequestEntity.get(uri("/greeting"))
                .header(HttpHeaders.ORIGIN, "http://localhost:9000")
                .build(),
            Greeting::class.java
        )

        assertThat(entity.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(entity.headers.accessControlAllowOrigin).isEqualTo("http://localhost:9000")

        entity.body?.let { greeting ->
            assertThat(greeting.content).isEqualTo("Hello, World!")
        } ?: error("Response body should not be null")
    }

    @Test
    fun `cors with javaconfig`() {
        val entity = restTemplate.exchange(
            RequestEntity.get(uri("/greeting-javaconfig"))
                .header(HttpHeaders.ORIGIN, "http://localhost:9000")
                .build(),
            Greeting::class.java
        )

        assertThat(entity.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(entity.headers.accessControlAllowOrigin).isEqualTo("http://localhost:9000")

        entity.body?.let { greeting ->
            assertThat(greeting.content).isEqualTo("Hello, World!")
        } ?: error("Response body should not be null")
    }

    private fun uri(path: String): URI =
        restTemplate.restTemplate.uriTemplateHandler.expand(path)
}