package com.example.restservicecors

import java.util.concurrent.atomic.AtomicLong
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class GreetingController {

    private val counter = AtomicLong()

    @CrossOrigin(origins = ["http://localhost:9000"])
    @GetMapping("/greeting")
    fun greeting(@RequestParam(required = false, defaultValue = "World") name: String): Greeting {
        println("==== get greeting ====")
        return Greeting(id = counter.incrementAndGet(), content = "Hello, $name!")
    }

    @GetMapping("/greeting-javaconfig")
    fun greetingWithJavaconfig(@RequestParam(required = false, defaultValue = "World") name: String): Greeting {
        println("==== in greeting ====")
        return Greeting(id = counter.incrementAndGet(), content = "Hello, $name!")
    }
}