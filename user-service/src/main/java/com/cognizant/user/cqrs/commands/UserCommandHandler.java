package com.cognizant.user.cqrs.commands;

import org.springframework.stereotype.Service;

import com.cognizant.cqrs.core.handlers.EventSourcingHandler;
import com.cognizant.user.cqrs.aggreagate.UserAggregate;

@Service
public class UserCommandHandler implements CommandHandler{

	private EventSourcingHandler<UserAggregate> eventSourcingHandler;
	
	UserCommandHandler(EventSourcingHandler<UserAggregate> eventSourcingHandler) {
		this.eventSourcingHandler = eventSourcingHandler;
	}

	@Override
	public void handle(UserAddCommand command) {
		var aggregate = new UserAggregate(command);
		eventSourcingHandler.save(aggregate);
	}

	@Override
	public void handle(UserDeleteCommand command) {
		var aggregate = eventSourcingHandler.getById(command.getId());
		aggregate.delete();
		eventSourcingHandler.save(aggregate);
	}
	 

}
