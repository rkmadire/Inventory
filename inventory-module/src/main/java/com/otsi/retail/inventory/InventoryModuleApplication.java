package com.otsi.retail.inventory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class InventoryModuleApplication {

	public static void main(String[] args) {
		SpringApplication.run(InventoryModuleApplication.class, args);
	}

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();

	}

}
