package com.cognizant.product;

import javax.annotation.PostConstruct;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import com.cognizant.cqrs.core.infrastructure.CommandDispatcher;
import com.cognizant.product.cqrs.commands.CommandHandler;
import com.cognizant.product.cqrs.commands.ProductAddCommand;
import com.cognizant.product.cqrs.commands.ProductDeleteCommand;

import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

@EnableSwagger2WebMvc
@EnableFeignClients
@EnableEurekaClient
@EnableConfigurationProperties
@EnableJpaAuditing
@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class ProductServiceApplication {

	private CommandDispatcher commandDispatcher;
	private CommandHandler commandHandler;
	
	ProductServiceApplication(CommandDispatcher commandDispatcher, CommandHandler commandHandler) {
		this.commandDispatcher = commandDispatcher;
		this.commandHandler = commandHandler;
	}
	
	public static void main(String[] args) {
		SpringApplication.run(ProductServiceApplication.class, args);
	}
	
	@PostConstruct
	public void registerHandlers() {
		commandDispatcher.registerHandler(ProductAddCommand.class, commandHandler::handle);
		commandDispatcher.registerHandler(ProductDeleteCommand.class, commandHandler::handle);
	}

}
