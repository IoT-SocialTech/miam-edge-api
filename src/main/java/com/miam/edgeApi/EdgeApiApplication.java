package com.miam.edgeApi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(scanBasePackages = "com.miam.edgeApi")
@EnableTransactionManagement
public class EdgeApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(EdgeApiApplication.class, args);
	}

}
