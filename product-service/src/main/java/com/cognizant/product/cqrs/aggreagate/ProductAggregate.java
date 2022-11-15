package com.cognizant.product.cqrs.aggreagate;

import java.util.Date;

import com.cognizant.cqrs.core.aggregate.AggregateRoot;
import com.cognizant.product.cqrs.commands.ProductAddCommand;
import com.cognizant.product.cqrs.events.ProductAddEvent;
import com.cognizant.product.cqrs.events.ProductDeleteEvent;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ProductAggregate extends AggregateRoot {

	private String productId;

	private String shortDescription;

	private String detailedDescription;

	private String categopry;

	private Double startingPrice;

	private Date bidEndDate;

	private String sellerId;

	public ProductAggregate(ProductAddCommand command) {
		raiseEvent(ProductAddEvent.builder()
			.productId(command.getId())
			.shortDescription(command.getShortDescription())
			.detailedDescription(command.getDetailedDescription())
			.categopry(command.getCategopry())
			.startingPrice(command.getStartingPrice())
			.bidEndDate(command.getBidEndDate())
			.sellerId(command.getSellerId()).build()
		);
	}

	public void apply(ProductAddEvent event) {
		this.productId = event.getProductId();
		this.shortDescription = event.getShortDescription();
		this.detailedDescription = event.getDetailedDescription();
		this.categopry = event.getCategopry();
		this.startingPrice = event.getStartingPrice();
		this.bidEndDate = event.getBidEndDate();
		this.sellerId = event.getSellerId();
	}
	
	public void delete() {
		raiseEvent(ProductDeleteEvent.builder()
			.id(this.id)
			.build()
		);
	}
	
	public void apply(ProductDeleteEvent event) {
		this.id = event.getId();
	}
}
