package com.example.visibility;

import com.example.visibility.service.VisibilityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.stream.Collectors;

@SpringBootApplication
@Slf4j
public class VisibilityApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(VisibilityApplication.class, args);
	}

	@Autowired
	private VisibilityService visibilityService;

	@Override
	public void run(String... args) {
		log.info("Executing visibility algorithm");

		String result = visibilityService.getVisibleProducts()
			.stream()
			.map(Object::toString)
			.collect(Collectors.joining(","));

		log.info("Visible products: {}", result);

		log.info("End visibility algorithm");
	}
}
