package com.flightright.rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.flightright")
public class FlightRightRestApplication {

	public static void main(String[] args) {
		SpringApplication.run(FlightRightRestApplication.class, args);
	}

}
