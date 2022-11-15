package com.cognizant.seller.cqrs.commands;

public interface CommandHandler {
    void handle(SellerAddCommand command);

//    void handle(SellerDeleteCommand command);

}
