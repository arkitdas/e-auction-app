package com.cognizant.product.cqrs.commands;

import org.springframework.stereotype.Service;

import com.cognizant.cqrs.core.handlers.EventSourcingHandler;
import com.cognizant.product.cqrs.aggreagate.ProductAggregate;

@Service
public class ProductCommandHandler implements CommandHandler{

	private EventSourcingHandler<ProductAggregate> eventSourcingHandler;

	@Override
	public void handle(ProductAddCommand command) {
		var aggregate = new ProductAggregate(command);
		eventSourcingHandler.save(aggregate);
	}

	@Override
	public void handle(ProductDeleteCommand command) {
		var aggregate = eventSourcingHandler.getById(command.getId());
        aggregate.delete();
        eventSourcingHandler.save(aggregate);
	}

}
