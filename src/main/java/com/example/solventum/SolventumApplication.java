package com.example.solventum;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class SolventumApplication {

	public static int concurrencies = 10;	// The amount of concurrent requests that can be made. Default is 10 because that is the example given in the question

	public static void main(String[] args) {
		// I think there is a way to retrieve arguments in Spring. I decided to do the default Java way
		for (String arg : args) {
			if (arg.startsWith("--max=")) {
				String maximum = arg.split("=")[1];
				concurrencies = Integer.parseInt(maximum);
			}
		}
		SpringApplication.run(SolventumApplication.class, args);
	}
}
