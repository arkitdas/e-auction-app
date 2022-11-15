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
	
	private boolean active;

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
		this.id = event.getProductId();
		this.productId = event.getProductId();
		this.shortDescription = event.getShortDescription();
		this.detailedDescription = event.getDetailedDescription();
		this.categopry = event.getCategopry();
		this.startingPrice = event.getStartingPrice();
		this.bidEndDate = event.getBidEndDate();
		this.sellerId = event.getSellerId();
		this.active = true;
	}
	
	public void delete() {
		if (!this.active) {
            throw new IllegalStateException("Product has already been deleted!");
        }
		raiseEvent(ProductDeleteEvent.builder()
			.id(this.id)
			.build()
		);
	}
	
	public void apply(ProductDeleteEvent event) {
		this.id = event.getId();
		this.active = false;
	}
}
