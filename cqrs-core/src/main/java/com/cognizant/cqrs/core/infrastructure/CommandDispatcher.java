package com.cognizant.cqrs.core.infrastructure;

import com.cognizant.cqrs.core.commands.BaseCommand;
import com.cognizant.cqrs.core.commands.CommandHandlerMethod;

public interface CommandDispatcher {
    <T extends BaseCommand> void registerHandler(Class<T> type, CommandHandlerMethod<T> handler);
    void send(BaseCommand command);
}
