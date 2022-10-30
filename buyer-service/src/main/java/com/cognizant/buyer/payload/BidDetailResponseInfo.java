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
public class BidDetailResponseInfo {

	private String bidId;
	
	private String productId;
	
	private double bidAmount;
	
    private BuyerResponseInfo buyer;
}
