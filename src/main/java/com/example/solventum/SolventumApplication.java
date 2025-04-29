package com.example.solventum;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class SolventumApplication {

	public static void main(String[] args) {
		SpringApplication.run(SolventumApplication.class, args);
	}
	@GetMapping("/encode")
	public String encode(@RequestParam(value = "url", defaultValue = "https://example.com/library/react") String urlString) {
		return String.format("url: %s", urlString);
	}

	@GetMapping("/decode")
	public String decode(@RequestParam(value = "url", defaultValue = "http://short.est/GeAi9K") String urlString) {
		return String.format("url: %s", urlString);
	}

}
