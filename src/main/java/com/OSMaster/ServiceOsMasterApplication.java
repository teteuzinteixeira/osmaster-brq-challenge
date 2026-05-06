package com.OSMaster;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ServiceOsMasterApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServiceOsMasterApplication.class, args);
	}

}
