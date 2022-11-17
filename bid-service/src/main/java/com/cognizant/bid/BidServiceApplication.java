package com.cognizant.bid;

import javax.annotation.PostConstruct;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import com.cognizant.bid.cqrs.commands.BidAddCommand;
import com.cognizant.bid.cqrs.commands.BidUpdateAmountCommand;
import com.cognizant.bid.cqrs.commands.CommandHandler;
import com.cognizant.cqrs.core.infrastructure.CommandDispatcher;

import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

@EnableSwagger2WebMvc
@EnableFeignClients
@EnableEurekaClient
@EnableConfigurationProperties
@EnableJpaAuditing
@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class BidServiceApplication {

	private CommandDispatcher commandDispatcher;
	private CommandHandler commandHandler;
	
	BidServiceApplication(CommandDispatcher commandDispatcher, CommandHandler commandHandler) {
		this.commandDispatcher = commandDispatcher;
		this.commandHandler = commandHandler;
	}
	
	public static void main(String[] args) {
		SpringApplication.run(BidServiceApplication.class, args);
	}
	
	@PostConstruct
	public void registerHandlers() {
		commandDispatcher.registerHandler(BidAddCommand.class, commandHandler::handle);
		commandDispatcher.registerHandler(BidUpdateAmountCommand.class, commandHandler::handle);
	}

}
