package com.cognizant.seller.cqrs.events;

import com.cognizant.cqrs.core.events.BaseEvent;
import com.cognizant.seller.payload.ProductInfo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class SellerAddEvent extends BaseEvent {

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
}
