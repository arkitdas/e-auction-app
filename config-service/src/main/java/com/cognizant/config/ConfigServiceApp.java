package com.cognizant.config;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
@EnableConfigServer
public class ConfigServiceApp {

	public static void main(String[] args) {
		new SpringApplicationBuilder(ConfigServiceApp.class).run(args);
	}

}