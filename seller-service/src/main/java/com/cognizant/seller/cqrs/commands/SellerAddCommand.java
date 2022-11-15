package com.cognizant.seller.cqrs.commands;

import com.cognizant.cqrs.core.commands.BaseCommand;
import com.cognizant.seller.payload.ProductInfo;

import lombok.Data;

@Data
public class SellerAddCommand extends BaseCommand {

	private String firstName;
	
	private String lastName;
	
	private String address;
	
	private String city;
	
	private String state;
	
	private String pin;
	
	private String phone;
	
	private String email;
	
	private ProductInfo product;
}
