package com.cognizant.bid;

import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.annotation.PostConstruct;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.commons.util.InetUtils;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.eureka.EurekaInstanceConfigBean;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
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
	
	@Bean
	public EurekaInstanceConfigBean eurekaInstanceConfig(InetUtils inetUtils) {
		EurekaInstanceConfigBean config = new EurekaInstanceConfigBean(inetUtils);
		String ip = null;
		try {
			ip = InetAddress.getLocalHost().getHostAddress();
			System.out.println("Ip Address : "+ip);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		config.setIpAddress(ip);
		config.setPreferIpAddress(true);
		config.setNonSecurePort(8010);
		config.setAppname("bid-service");
		return config;
	}

}
