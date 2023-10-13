package com.currncyconversions.SCurrencyConversion;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class SCurrencyConversionApplication {

	public static void main(String[] args) {
		SpringApplication.run(SCurrencyConversionApplication.class, args);
	}



}
