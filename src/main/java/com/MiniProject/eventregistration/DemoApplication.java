package com.MiniProject.eventregistration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication(scanBasePackages = "com.MiniProject.eventregistration")
@EnableJpaRepositories(basePackages =
		"com.MiniProject.eventregistration.repository")
@EnableMongoRepositories(basePackages =
		"com.MiniProject.eventregistration.mongo.repository_mongo")
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

}