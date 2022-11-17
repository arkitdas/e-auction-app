package com.cognizant.bid.cqrs.commands;

import org.springframework.stereotype.Service;

import com.cognizant.bid.cqrs.aggreagate.BidAggregate;
import com.cognizant.cqrs.core.handlers.EventSourcingHandler;

@Service
public class BidCommandHandler implements CommandHandler{

	private EventSourcingHandler<BidAggregate> eventSourcingHandler;
	
	BidCommandHandler(EventSourcingHandler<BidAggregate> eventSourcingHandler) {
		this.eventSourcingHandler = eventSourcingHandler;
	}

	@Override
	public void handle(BidAddCommand command) {
		var aggregate = new BidAggregate(command);
		eventSourcingHandler.save(aggregate);
	}

	@Override
	public void handle(BidUpdateAmountCommand command) {
		var aggregate = eventSourcingHandler.getById(command.getProductId());
        aggregate.updateBidAmount(command.getProductId(), command.getBidAmount(), command.getEmail());
        eventSourcingHandler.save(aggregate);
	}

}
