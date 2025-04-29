package com.example.solventum.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.solventum.exceptions.TooManyRequestsException;
import com.example.solventum.semaphore.MySemaphore;
import com.example.solventum.service.RandomURLService;

@RestController
public class MyController {

    private final RandomURLService randomURLService;

    private final MySemaphore concurrentLock;

    public MyController(MySemaphore concurrentLock, RandomURLService randomURLService) {
        // I don't know if the requirements want me to make two semaphores, one for each endpoint or just one total
        // Because only one parameter is given in the example, I'm assuming it's just one lock for both
        // This is something that I would clarify ahead of time if I could
        this.concurrentLock = concurrentLock;
        this.randomURLService = randomURLService;
    }

    @GetMapping("/encode")
	public String encode(@RequestParam(value = "url", defaultValue = "https://example.com/library/react") String urlString) {
        if (concurrentLock.tryAcquire()) {
            try {
                return randomURLService.getShortUrl(urlString);
            } finally {
                concurrentLock.release();
            }
        } else {
            throw new TooManyRequestsException();
        }
	}

    // A get endpoint with the specified name. I decided to use a Request parameter to retrieve the url. I just like them and I like the default value part.
	@GetMapping("/decode")
	public String decode(@RequestParam(value = "url", defaultValue = "http://short.est/GeAi9K") String urlString) {
		if (concurrentLock.tryAcquire()) {
            try {
                return randomURLService.getLongUrl(urlString);
            } finally {
                concurrentLock.release();
            }
        } else {
            throw new TooManyRequestsException();
        }
	}
}
