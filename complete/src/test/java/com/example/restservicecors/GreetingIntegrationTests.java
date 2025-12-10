package com.example.restservicecors;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.client.RestTestClient;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureRestTestClient
public class GreetingIntegrationTests {

	@Autowired
	private RestTestClient testClient;

	@Test
	public void corsWithAnnotation() throws Exception {
		this.testClient.get().uri("/greeting")
				.header(HttpHeaders.ORIGIN, "http://localhost:9000")
				.exchangeSuccessfully()
				.expectHeader()
				.valueEquals(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "http://localhost:9000")
				.expectBody(Greeting.class)
				.value(greeting -> {
					Assertions.assertNotNull(greeting);
					assertEquals("Hello, World!", greeting.content());
				});
	}

	@Test
	public void corsWithJavaconfig() {
		this.testClient.get().uri("/greeting-javaconfig")
				.header(HttpHeaders.ORIGIN, "http://localhost:9000")
				.exchangeSuccessfully()
				.expectHeader()
				.valueEquals(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "http://localhost:9000")
				.expectBody(Greeting.class)
				.value(greeting -> {
					Assertions.assertNotNull(greeting);
					assertEquals("Hello, World!", greeting.content());
				});
	}

}
