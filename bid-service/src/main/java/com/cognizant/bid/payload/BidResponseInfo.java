package com.cognizant.bid.payload;

import java.util.Date;

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
	
	private Date createdDate;
	
	private UserResponseInfo buyer;
}
