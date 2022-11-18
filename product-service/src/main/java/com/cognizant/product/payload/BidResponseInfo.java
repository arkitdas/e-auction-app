package com.cognizant.product.payload;

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
public class BidResponseInfo {

	private String bidId;
	
	private String productId;
	
	private double bidAmount;
	
	private UserResponseInfo buyer;
}
