package com.example.restservicecors

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpHeaders
import org.springframework.test.web.servlet.client.RestTestClient
import org.springframework.test.web.servlet.client.expectBody

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureRestTestClient
class GreetingIntegrationTests @Autowired constructor(
    private val testClient: RestTestClient
) {

    @Test
    fun `cors with annotation`() {
        this.testClient.get().uri("/greeting")
            .header(HttpHeaders.ORIGIN, "http://localhost:9000")
            .exchangeSuccessfully()
            .expectHeader()
            .valueEquals(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "http://localhost:9000")
            .expectBody<Greeting>()
            .value { greeting: Greeting? ->
                Assertions.assertNotNull(greeting)
                assertEquals("Hello, World!", greeting!!.content)
            }
    }

    @Test
    fun `cors with javaconfig`() {
        this.testClient.get().uri("/greeting-javaconfig")
            .header(HttpHeaders.ORIGIN, "http://localhost:9000")
            .exchangeSuccessfully()
            .expectHeader()
            .valueEquals(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "http://localhost:9000")
            .expectBody<Greeting>()
            .value { greeting: Greeting? ->
                Assertions.assertNotNull(greeting)
                assertEquals("Hello, World!", greeting!!.content)
            }
    }
}