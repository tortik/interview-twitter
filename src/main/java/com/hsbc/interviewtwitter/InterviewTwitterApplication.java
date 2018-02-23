package com.hsbc.interviewtwitter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan(basePackages = "com.hsbc.interviewtwitter")
public class InterviewTwitterApplication {

	public static void main(String[] args) {
		SpringApplication.run(InterviewTwitterApplication.class, args);
	}
}
