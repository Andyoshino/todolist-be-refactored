package com.nogroup.todolistbe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "web")
public class TodolistBeApplication {

	public static void main(String[] args) {
		SpringApplication.run(TodolistBeApplication.class, args);
	}

}
