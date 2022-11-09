package com.cognizant.product.cqrs.events;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductAddEvent {

	private String productId;
	
	private String shortDescription;
	
	private String detailedDescription;
	
	private String categopry;
	
	private Double startingPrice;
	
	private Date bidEndDate;
	
	private String sellerId;
}
