package com.cognizant.buyer.cqrs.commands;

import com.cognizant.cqrs.core.commands.BaseCommand;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BidUpdateAmountCommand extends BaseCommand {

	private String productId;
	
	private String email;
	
	private double bidAmount;
}
