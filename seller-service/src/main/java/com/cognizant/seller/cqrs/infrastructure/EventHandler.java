package com.cognizant.seller.cqrs.infrastructure;

import com.cognizant.seller.cqrs.events.SellerAddEvent;
import com.cognizant.seller.cqrs.events.SellerDeleteEvent;

public interface EventHandler {
    void on(SellerAddEvent event);
//    void on(SellerDeleteEvent event);
}
