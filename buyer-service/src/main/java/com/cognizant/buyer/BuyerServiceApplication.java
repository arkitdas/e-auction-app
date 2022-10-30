package com.cognizant.buyer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

@EnableSwagger2WebMvc
@EnableFeignClients
@EnableEurekaClient
@EnableConfigurationProperties
@EnableJpaAuditing
@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class BuyerServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(BuyerServiceApplication.class, args);
	}

}
