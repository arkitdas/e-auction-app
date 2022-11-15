package com.cognizant.buyer.cqrs.commands;

import com.cognizant.buyer.payload.BidDetailsInfo;
import com.cognizant.buyer.payload.BuyerInfo;
import com.cognizant.cqrs.core.commands.BaseCommand;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BidAddCommand extends BaseCommand {

	private BuyerInfo buyer;
	
	private BidDetailsInfo bidDetails;
}
