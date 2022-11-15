package com.cognizant.buyer.cqrs.commands;

import com.cognizant.cqrs.core.commands.BaseCommand;

import lombok.Data;

@Data
public class BuyerAddCommand extends BaseCommand {

	private String firstName;
	
	private String lastName;
	
	private String address;
	
	private String city;
	
	private String state;
	
	private String pin;
	
	private String phone;
	
	private String email;
	
}
