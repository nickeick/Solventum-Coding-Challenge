package com.example.solventum.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.solventum.SolventumApplication;

@RestController
public class MyController {

    @GetMapping("/encode")
	public String encode(@RequestParam(value = "url", defaultValue = "https://example.com/library/react") String urlString) {
		return String.format("url: %s", urlString);
	}

    // A get endpoint with the specified name. I decided to use a Request parameter to retrieve the url. I just like them and I like the default value part.
	@GetMapping("/decode")
	public String decode(@RequestParam(value = "url", defaultValue = "http://short.est/GeAi9K") String urlString) {
		return String.format("url: %s", urlString);
	}
}
