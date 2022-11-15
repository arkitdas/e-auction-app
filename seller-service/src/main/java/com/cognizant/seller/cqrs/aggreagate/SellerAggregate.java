package com.cognizant.seller.cqrs.aggreagate;

import com.cognizant.cqrs.core.aggregate.AggregateRoot;
import com.cognizant.seller.cqrs.commands.SellerAddCommand;
import com.cognizant.seller.cqrs.events.SellerAddEvent;
import com.cognizant.seller.cqrs.events.SellerDeleteEvent;
import com.cognizant.seller.payload.ProductInfo;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class SellerAggregate extends AggregateRoot {

	private String sellerId;
	
	private String firstName;
	
	private String lastName;
	
	private String address;
	
	private String city;
	
	private String state;
	
	private String pin;
	
	private String phone;
	
	private String email;
	
	private ProductInfo product;

	public SellerAggregate(SellerAddCommand command) {
		raiseEvent(SellerAddEvent.builder()
			.sellerId(command.getId())
			.firstName(command.getFirstName())
			.lastName(command.getLastName())
			.address(command.getAddress())
			.city(command.getCity())
			.state(command.getState())
			.pin(command.getPin())
			.phone(command.getPhone())
			.email(command.getEmail())
			.product(command.getProduct())
			.build()
		);
	}

	public void apply(SellerAddEvent event) {
		this.sellerId = event.getSellerId();
		this.firstName = event.getFirstName();
		this.lastName = event.getLastName();
		this.address = event.getAddress();
		this.city = event.getCity();
		this.state = event.getState();
		this.pin = event.getPin();
		this.phone = event.getPhone();
		this.email = event.getEmail();
	}
	
	public void delete() {
		raiseEvent(SellerDeleteEvent.builder()
			.id(this.id)
			.build()
		);
	}
	
	public void apply(SellerDeleteEvent event) {
		this.id = event.getId();
	}
}
