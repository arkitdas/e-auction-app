package com.cognizant.buyer.cqrs.infrastructure;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.cognizant.cqrs.core.commands.BaseCommand;
import com.cognizant.cqrs.core.commands.CommandHandlerMethod;
import com.cognizant.cqrs.core.infrastructure.CommandDispatcher;

@Service
public class BuyerCommandDispatcher implements CommandDispatcher{

	/**
	 * All routes required to be registered
	 */
	private final Map<Class<? extends BaseCommand>, List<CommandHandlerMethod>> routes = new HashMap<>();
	
	
	@Override
    public <T extends BaseCommand> void registerHandler(Class<T> type, CommandHandlerMethod<T> handler) {
        var handlers = routes.computeIfAbsent(type, c -> new LinkedList<>());
        handlers.add(handler);
    }

    @SuppressWarnings("unchecked")
	@Override
    public void send(BaseCommand command) {
        var handlers = routes.get(command.getClass());
        /**
         * Check if a minimum handler exists for a command
         */
        if (handlers == null || handlers.size() == 0) {
            throw new RuntimeException("No command handler was registered!");
        }
        /**
         * Check if has more than two handlers
         */
        if (handlers.size() > 1) {
            throw new RuntimeException("Cannot send command to more than one handler!");
        }
        handlers.get(0).handle(command);
    }

}
