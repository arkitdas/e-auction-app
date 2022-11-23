package com.cognizant.product.cqrs.commands;

public interface CommandHandler {
    void handle(ProductAddCommand command);

    void handle(ProductDeleteCommand command);

}
