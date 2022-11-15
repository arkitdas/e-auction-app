package com.cognizant.buyer.cqrs.commands;

public interface CommandHandler {
    void handle(BuyerAddCommand command);

    void handle(BidAddCommand command);
    
    void handle(BidUpdateAmountCommand command);

}
