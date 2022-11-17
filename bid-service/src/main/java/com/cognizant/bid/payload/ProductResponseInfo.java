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
public class ProductResponseInfo {

	private String productId;
	
	private String productName;
	
	private String shortDescription;
	
	private String detailedDescription;
	
	private String categopry;
	
	private Double startingPrice;
	
	private Date bidEndDate;
	
	private String sellerId;
}
