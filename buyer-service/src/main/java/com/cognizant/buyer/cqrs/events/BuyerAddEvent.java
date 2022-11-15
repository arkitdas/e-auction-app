package com.cognizant.buyer.cqrs.events;

import com.cognizant.cqrs.core.events.BaseEvent;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class BuyerAddEvent extends BaseEvent {

	private String buyerId;
	private String firstName;
	private String lastName;
	private String address;
	private String city;
	private String state;
	private String pin;
	private String phone;
	private String email;
}
