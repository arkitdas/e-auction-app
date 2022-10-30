package com.cognizant.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
@EnableConfigurationProperties
public class GatewayServiceApp {

	public static void main(String[] args) {
		SpringApplication.run(GatewayServiceApp.class, args);
	}

}
