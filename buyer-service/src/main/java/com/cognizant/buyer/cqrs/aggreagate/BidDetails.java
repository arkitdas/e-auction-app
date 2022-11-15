package com.cognizant.buyer.cqrs.aggreagate;

import javax.validation.constraints.NotNull;

import com.cognizant.buyer.payload.BuyerInfo;

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
public class BidDetails {

	private String productId;
	
	private double bidAmount;
	
	private String bidId;
}
