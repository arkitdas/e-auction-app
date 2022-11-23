package com.cognizant.product.cqrs.events;

import java.util.Date;

import com.cognizant.cqrs.core.events.BaseEvent;
import com.cognizant.product.payload.UserRequestInfo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class ProductAddEvent extends BaseEvent {
	
	private String productId;
	
	private String productName;

	private String shortDescription;

	private String detailedDescription;

	private String categopry;

	private Double startingPrice;

	private Date bidEndDate;

	private UserRequestInfo seller;
}
