package hello;

// https://spring.io/guides/gs/rest-service-cors/

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {

	private static final String template = "Hello, %s!";
	private final AtomicLong counter = new AtomicLong();

	@Autowired
	private GreetingService greetingService;

	@CrossOrigin(origins = "http://localhost:9000")
	@GetMapping("/greeting")
	public Greeting greeting(@RequestParam(required=false, defaultValue="World") String name) {
		System.out.println("==== in greeting ====");
//		return new Greeting(counter.incrementAndGet(), String.format(template, name));
		return greetingService.getGreeting(name);
	}

	// mapping to localhost:9000 found in CORS hello/Application.java and called in hello.js
	@GetMapping("/greeting-javaconfig")
	public Greeting greetingWithJavaconfig(@RequestParam(required=false, defaultValue="World") String name) {
		System.out.println("==== in greeting (javaconfig) ====");
//		return new Greeting(counter.incrementAndGet(), String.format(template, name));
		return greetingService.getGreeting(name);
	}

}
