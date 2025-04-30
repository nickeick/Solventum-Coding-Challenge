package com.example.solventum.controller;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.solventum.data.MyResponse;
import com.example.solventum.exceptions.TooManyRequestsException;
import com.example.solventum.semaphore.MySemaphore;
import com.example.solventum.service.RandomURLService;

@RestController
public class MyController {

    private final RandomURLService randomURLService;

    private final MySemaphore concurrentLock;

    private final Pattern pattern = Pattern.compile(".+\\..+");

    public MyController(MySemaphore concurrentLock, RandomURLService randomURLService) {
        // I don't know if the requirements want me to make two semaphores, one for each endpoint or just one total
        // Because only one parameter is given in the example, I'm assuming it's just one lock for both
        // This is something that I would clarify ahead of time if I could
        this.concurrentLock = concurrentLock;
        this.randomURLService = randomURLService;
    }

    @GetMapping("/encode")
	public MyResponse encode(@RequestParam(value = "url", defaultValue = "https://example.com/library/react") String urlString) {
        Matcher matcher = pattern.matcher(urlString);
        // I decided to pattern match to check that the input is a valid URL "example.com"
        // I thought about checking to see if the input would be able to complete a valid request, however
        // people could use this service for a domain that isn't registered or something similar, so I decided not to
        if (matcher.find()) {
            if (concurrentLock.tryAcquire()) {
                try {
                    return new MyResponse(randomURLService.getShortUrl(urlString));
                } finally {
                    concurrentLock.release();
                }
            } else {
                throw new TooManyRequestsException();
            }
        } else {
            // If the requirements specified a need for verbose responses, I would modify the error controller to include this reason in the response
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid URL");
        }
	}

    // A get endpoint with the specified name. I decided to use a Request parameter to retrieve the url. I just like them and I like the default value part.
	@GetMapping("/decode")
	public MyResponse decode(@RequestParam(value = "url", defaultValue = "http://short.est/GeAi9K") String urlString) {
		if (concurrentLock.tryAcquire()) {
            try {
                return new MyResponse(randomURLService.getLongUrl(urlString));
            } finally {
                concurrentLock.release();
            }
        } else {
            throw new TooManyRequestsException();
        }
	}
}
