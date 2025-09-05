package com.example.restservicecors

import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.concurrent.atomic.AtomicLong

@RestController
class GreetingController {

    private val template = "Hello, %s!"
    private val counter = AtomicLong()

    @CrossOrigin(origins = ["http://localhost:9000"])
    @GetMapping("/greeting")
    fun greeting(@RequestParam(required = false, defaultValue = "World") name: String): Greeting {
        println("==== get greeting ====")
        return Greeting(counter.incrementAndGet(), String.format(template, name))
    }

    @GetMapping("/greeting-javaconfig")
    fun greetingWithJavaconfig(@RequestParam(required = false, defaultValue = "World") name: String): Greeting {
        println("==== in greeting ====")
        return Greeting(counter.incrementAndGet(), String.format(template, name))
    }
}
