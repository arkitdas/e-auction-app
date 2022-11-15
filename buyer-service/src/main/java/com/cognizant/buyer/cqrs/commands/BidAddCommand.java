package com.cognizant.buyer.cqrs.commands;

import com.cognizant.cqrs.core.commands.BaseCommand;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BidAddCommand extends BaseCommand {

	private String productId;
	
	private double bidAmount;
}
