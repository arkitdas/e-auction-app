package com.cognizant.buyer.cqrs.events;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class BidDetailEvent {
	
	private String bidId;
	
	private String productId;
	
	private double bidAmount;
}
