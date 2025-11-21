package com.example.restservicecors;

public record Greeting(long id, String content) {

	public Greeting() {
		this(-1, "");
	}
}
