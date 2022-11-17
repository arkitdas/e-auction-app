package com.cognizant.bid.cqrs.commands;

import com.cognizant.bid.payload.UserRequestInfo;
import com.cognizant.cqrs.core.commands.BaseCommand;

import lombok.Data;

@Data
public class BidAddCommand extends BaseCommand {

	private String productId;
	
	private double bidAmount;
	
	private UserRequestInfo buyer;
}
