package com.cognizant.bid.payload;

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
public class BidRequestInfo {

	private String productId;
	private double bidAmount;
	UserRequestInfo buyer;
	
}
