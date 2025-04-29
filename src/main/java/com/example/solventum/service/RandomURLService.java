package com.example.solventum.service;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public class RandomURLService {

    private static final String URL_START = "http://short.est/";
    private static final String CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int PATH_LENGTH = 6;    // I set this default to 6 because that is what is used in the example. I would clarify this if I am able to
    private static final SecureRandom random = new SecureRandom();
    private static Map<String, String> shortToLong = new HashMap<>();
    private static Map<String, String> longToShort = new HashMap<>();

    public String getShortUrl(String longUrl) {
        if (longToShort.containsKey(longUrl)) {
            return longToShort.get(longUrl);
        } else {
            // If a short url doesn't exist already, generate one and add it to both hashmaps
            String shortUrl = this.generateRandomURL();
            longToShort.put(longUrl, shortUrl);
            shortToLong.put(shortUrl, longUrl);
            return shortUrl;
        }
    }

    public String getLongUrl(String shortUrl) {
        if (shortToLong.containsKey(shortUrl)) {
            return shortToLong.get(shortUrl);
        } else {
            throw new RuntimeException();
        }
    }

    // This function generates a random path for the end of the 
    private String generateRandomURL() {
        StringBuilder URLBuilder = new StringBuilder(PATH_LENGTH);
        URLBuilder.append(URL_START);
        for (int i = 0; i < PATH_LENGTH; i++) {
            int index = random.nextInt(CHARACTERS.length());
            URLBuilder.append(CHARACTERS.charAt(index));
        }
        return URLBuilder.toString();
    }
}
