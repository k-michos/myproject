package com.demo.myproject.utils;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


//@SpringBootApplication()
@SpringBootApplication(scanBasePackages = "com.demo.*")
@EnableJpaRepositories("com.demo.myproject.repositories")
@EntityScan("com.demo.myproject.entities")
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

}
