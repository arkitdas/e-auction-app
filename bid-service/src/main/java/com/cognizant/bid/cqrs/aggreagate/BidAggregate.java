package com.cognizant.bid.cqrs.aggreagate;

import com.cognizant.bid.cqrs.commands.BidAddCommand;
import com.cognizant.bid.cqrs.events.BidAddEvent;
import com.cognizant.bid.cqrs.events.BidUpdateAmountdEvent;
import com.cognizant.bid.payload.UserRequestInfo;
import com.cognizant.cqrs.core.aggregate.AggregateRoot;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class BidAggregate extends AggregateRoot {

	private String productId;
	
	private double bidAmount;

	private UserRequestInfo buyer;
	
	public BidAggregate(BidAddCommand command) {
		raiseEvent(BidAddEvent.builder()
			.id(command.getBidId())
			.bidId(command.getBidId())
			.productId(command.getProductId())
			.bidAmount(command.getBidAmount())
			.buyer(command.getBuyer())
			.build()
		);
	}

	public void apply(BidAddEvent event) {
		this.id = event.getBidId();
		this.productId = event.getProductId();
		this.bidAmount = event.getBidAmount();
		this.buyer = event.getBuyer();
	}
	
	public void updateBidAmount(String productId, double bidAmmount, String emailId) {
		
		raiseEvent(BidUpdateAmountdEvent.builder()
			.id(this.id)
			.bidAmount(bidAmmount)
			.productId(productId)
			.email(emailId)
			.build()
		);
	}
	
	public void apply(BidUpdateAmountdEvent event) {
		this.id = event.getId();
		this.productId = event.getProductId();
		this.bidAmount = event.getBidAmount();
	}
}
