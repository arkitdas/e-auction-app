package com.cognizant.buyer.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BidDetailsResponseInfo {

	private String bidId;
	
	private Long productId;
	
	private double bidAmount;
}
