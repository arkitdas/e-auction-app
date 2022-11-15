package com.cognizant.buyer.cqrs.aggreagate;

import com.cognizant.buyer.cqrs.commands.BuyerAddCommand;
import com.cognizant.buyer.cqrs.events.BidAddEvent;
import com.cognizant.buyer.cqrs.events.BidUpdateAmountdEvent;
import com.cognizant.buyer.cqrs.events.BuyerAddEvent;
import com.cognizant.cqrs.core.aggregate.AggregateRoot;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class BuyerAggregate extends AggregateRoot {

	private String buyerId;
	
	private String firstName;
	
	private String lastName;
	
	private String address;
	
	private String city;
	
	private String state;
	
	private String pin;
	
	private String phone;
	
	private String email;
	
	private String productId;
	
	private double bidAmount;
	
	private String bidId;

	public BuyerAggregate(BuyerAddCommand command) {
		raiseEvent(BuyerAddEvent.builder()
			.buyerId(command.getId())
			.firstName(command.getFirstName())
			.lastName(command.getLastName())
			.address(command.getAddress())
			.city(command.getCity())
			.state(command.getState())
			.pin(command.getPin())
			.phone(command.getPhone())
			.email(command.getEmail())
			.build()
		);
	}

	public void apply(BuyerAddEvent event) {
		this.buyerId = event.getBuyerId();
		this.firstName = event.getFirstName();
		this.lastName = event.getLastName();
		this.address = event.getAddress();
		this.city = event.getCity();
		this.state = event.getState();
		this.pin = event.getPin();
		this.phone = event.getPhone();
		this.email = event.getEmail();
	}
	
	public void addBid(String productId, double bidAmount) {
		raiseEvent(BidAddEvent.builder()
			.id(this.id)
			.bidAmount(bidAmount)
			.productId(productId)
			.build()
		);
	}
	
	public void apply(BidAddEvent event) {
		this.bidId = event.getId();
		this.bidAmount = event.getBidAmount();
		this.productId = event.getProductId();
	}
	
	public void updateBidAmount(String productId, double bidAmount) {
		raiseEvent(BidUpdateAmountdEvent.builder()
			.bidId(this.bidId)
			.bidAmount(bidAmount)
			.productId(productId)
			.build()
		);
	}
	
	public void apply(BidUpdateAmountdEvent event) {
		this.bidId = event.getId();
		this.bidAmount = event.getBidAmount();
		this.productId = event.getProductId();
		this.email = event.getEmail();
		this.productId = event.getProductId();
	}
}
