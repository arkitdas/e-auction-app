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
public class BidAddEvent extends BaseEvent {

	private String productId;
	
	private double bidAmount;
	
}
