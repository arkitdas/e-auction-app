package com.cognizant.gateway;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.commons.util.InetUtils;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.eureka.EurekaInstanceConfigBean;
import org.springframework.context.annotation.Bean;

@EnableEurekaClient
@SpringBootApplication /* (exclude = { SecurityAutoConfiguration.class }) */
@EnableConfigurationProperties
public class GatewayServiceApp {

	public static void main(String[] args) {
		System.out.println("Gateway application initiating");
		SpringApplication.run(GatewayServiceApp.class, args);
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
		config.setNonSecurePort(8090);
		config.setAppname("gateway-service");
		return config;
	}

}
