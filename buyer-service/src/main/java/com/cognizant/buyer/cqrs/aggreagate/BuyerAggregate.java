package com.cognizant.buyer.cqrs.aggreagate;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import com.cognizant.buyer.cqrs.commands.BidAddCommand;
import com.cognizant.buyer.cqrs.events.BidAddEvent;
import com.cognizant.buyer.cqrs.events.BidDetailEvent;
import com.cognizant.buyer.cqrs.events.BidUpdateAmountdEvent;
import com.cognizant.buyer.cqrs.events.BuyerEvent;
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
	
	private List<BidDetails> bidDetails;
	
	public BuyerAggregate(BidAddCommand command) {
		BuyerEvent buyerEvent = BuyerEvent.builder()
				.buyerId(UUID.randomUUID().toString())
				.firstName(command.getBuyer().getFirstName())
				.lastName(command.getBuyer().getLastName())
				.address(command.getBuyer().getAddress())
				.city(command.getBuyer().getCity())
				.state(command.getBuyer().getState())
				.pin(command.getBuyer().getPin())
				.phone(command.getBuyer().getPhone())
				.email(command.getBuyer().getEmail())
				.build();
		BidDetailEvent bidDetailEvent = BidDetailEvent.builder()
				.bidId(UUID.randomUUID().toString())
				.bidAmount(command.getBidDetails().getBidAmount())
				.productId(command.getBidDetails().getProductId())
				.build();
		
		raiseEvent(BidAddEvent.builder().buyer(buyerEvent)
				.bidDetails(bidDetailEvent)
				.build());
	}

	public void apply(BidAddEvent event) {
		this.id = event.getBuyer().getBuyerId();
		this.buyerId = event.getBuyer().getBuyerId();
		this.firstName = event.getBuyer().getFirstName();
		this.lastName = event.getBuyer().getLastName();
		this.address = event.getBuyer().getAddress();
		this.city = event.getBuyer().getCity();
		this.state = event.getBuyer().getState();
		this.pin = event.getBuyer().getPin();
		this.phone = event.getBuyer().getPhone();
		this.email = event.getBuyer().getEmail();
		
		if(Objects.isNull(this.bidDetails)) {
			this.bidDetails = new ArrayList<>();
		}
		
		this.bidDetails.add(
				BidDetails.builder()
				.bidId(event.getBidDetails().getBidId())
				.bidAmount(event.getBidDetails().getBidAmount())
				.productId(event.getBidDetails().getProductId())
				.build()
		);
				
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
