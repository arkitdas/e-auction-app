package com.cognizant.product.cqrs.aggregates;


import static org.axonframework.modelling.command.AggregateLifecycle.apply;

import java.util.Date;
import java.util.UUID;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.modelling.command.TargetAggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;

import com.cognizant.product.cqrs.command.ProductAddCommand;
import com.cognizant.product.cqrs.events.ProductAddEvent;

@Aggregate
public class ProductAggregate {

	@TargetAggregateIdentifier
	private String productId;
	
	private String shortDescription;
	
	private String detailedDescription;
	
	private String categopry;
	
	private Double startingPrice;
	
	private Date bidEndDate;
	
	private String sellerId;
	
	@CommandHandler
    public void handle(ProductAddCommand command) {
		apply(ProductAddEvent.builder()
                .productId(UUID.randomUUID().toString())
                .shortDescription(command.getShortDescription())
                .detailedDescription(command.getDetailedDescription())
                .categopry(command.getCategopry())
                .startingPrice(command.getStartingPrice())
                .bidEndDate(command.getBidEndDate())
                .sellerId(command.getSellerId())
                .build());

    }
	
	@EventHandler
    public void on(ProductAddEvent event){
        this.productId = event.getProductId();
        this.shortDescription = event.getShortDescription();
        this.detailedDescription = event.getDetailedDescription();
        this.categopry = event.getCategopry();
        this.startingPrice = event.getStartingPrice();
        this.bidEndDate = event.getBidEndDate();
        this.sellerId = event.getSellerId();
    }
}
