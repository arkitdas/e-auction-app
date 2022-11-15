package com.cognizant.buyer.cqrs.commands;

import java.util.Objects;

import org.springframework.stereotype.Service;

import com.cognizant.cqrs.core.handlers.EventSourcingHandler;
import com.cognizant.buyer.cqrs.aggreagate.BuyerAggregate;

@Service
public class BuyerCommandHandler implements CommandHandler{

	private EventSourcingHandler<BuyerAggregate> eventSourcingHandler;
	
	BuyerCommandHandler(EventSourcingHandler<BuyerAggregate> eventSourcingHandler) {
		this.eventSourcingHandler = eventSourcingHandler;
	}


	@Override
	public void handle(BidAddCommand command) {
		var aggregate = eventSourcingHandler.getById(command.getId());
		if(Objects.isNull(aggregate)) {
			aggregate = new BuyerAggregate(command);
		}
        eventSourcingHandler.save(aggregate);
	}
	
	@Override
	public void handle(BidUpdateAmountCommand command) {
		var aggregate = eventSourcingHandler.getById(command.getId());
		aggregate.delete();
		eventSourcingHandler.save(aggregate);
	}
	 

}
