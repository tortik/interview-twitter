package com.hsbc.interviewtwitter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.hateoas.config.EnableHypermediaSupport;

@SpringBootApplication
@EnableAutoConfiguration
@EnableHypermediaSupport(type = EnableHypermediaSupport.HypermediaType.HAL)
@ComponentScan(basePackages = "com.hsbc.interviewtwitter")
public class InterviewTwitterApplication {

	public static void main(String[] args) {
		SpringApplication.run(InterviewTwitterApplication.class, args);
	}
}
