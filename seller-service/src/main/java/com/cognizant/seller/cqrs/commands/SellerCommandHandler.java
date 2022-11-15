package com.cognizant.seller.cqrs.commands;

import org.springframework.stereotype.Service;

import com.cognizant.cqrs.core.handlers.EventSourcingHandler;
import com.cognizant.seller.cqrs.aggreagate.SellerAggregate;

@Service
public class SellerCommandHandler implements CommandHandler{

	private EventSourcingHandler<SellerAggregate> eventSourcingHandler;
	
	SellerCommandHandler(EventSourcingHandler<SellerAggregate> eventSourcingHandler) {
		this.eventSourcingHandler = eventSourcingHandler;
	}

	@Override
	public void handle(SellerAddCommand command) {
		var aggregate = new SellerAggregate(command);
		eventSourcingHandler.save(aggregate);
	}

	/*
	 * @Override public void handle(SellerDeleteCommand command) { var aggregate =
	 * eventSourcingHandler.getById(command.getId()); aggregate.delete();
	 * eventSourcingHandler.save(aggregate); }
	 */

}
