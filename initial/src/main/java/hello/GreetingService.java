package hello;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class GreetingService {

	private final Map<String, Long> nameStore = new HashMap<>();

	private static final String template = "Hello, %s!";
	private final AtomicLong counter = new AtomicLong();

	public Greeting getGreeting(String name) {
		Greeting greeting;
		Long id;

		if(!nameStore.containsKey(name)) {// new
			id = counter.incrementAndGet();
			nameStore.put(name, id);
//			System.out.println("Adding " + name + " -> " + id + " to name store.");// TODO: 2/18/18 add loging
		} else {// exists
			id = nameStore.get(name);
		}

		return new Greeting(id, String.format(template, name));
	}

}
