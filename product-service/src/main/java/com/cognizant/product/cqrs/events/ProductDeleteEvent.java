package com.cognizant.product.cqrs.events;

import com.cognizant.cqrs.core.events.BaseEvent;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class ProductDeleteEvent  extends BaseEvent{

	private String productId;
	
	private String sellerId;
}
