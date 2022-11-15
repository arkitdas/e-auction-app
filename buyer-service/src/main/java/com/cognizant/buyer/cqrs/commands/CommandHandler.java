package com.cognizant.buyer.cqrs.commands;

public interface CommandHandler {
    void handle(BidAddCommand command);
    
    void handle(BidUpdateAmountCommand command);

}
