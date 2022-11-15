package com.cognizant.buyer.cqrs.infrastructure;

import com.cognizant.buyer.cqrs.events.BuyerAddEvent;
import com.cognizant.buyer.cqrs.events.SellerDeleteEvent;

public interface EventHandler {
    void on(BuyerAddEvent event);
//    void on(SellerDeleteEvent event);
}
