package de.wps.bikehh;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@SpringBootApplication
@EnableScheduling
public class BikehhApplication {

	public static void main(String[] args) {
		SpringApplication.run(BikehhApplication.class, args);
	}

}
