package com.example.canacetestdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class CanaceTestDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(CanaceTestDemoApplication.class, args);
	}

}
