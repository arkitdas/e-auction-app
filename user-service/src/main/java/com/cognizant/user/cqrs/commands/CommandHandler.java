package com.cognizant.user.cqrs.commands;

public interface CommandHandler {
    void handle(UserAddCommand command);
    void handle(UserDeleteCommand command);
}
