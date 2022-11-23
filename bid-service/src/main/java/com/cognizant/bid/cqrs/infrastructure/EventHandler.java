package com.cognizant.bid.cqrs.infrastructure;

import com.cognizant.bid.cqrs.events.BidAddEvent;
import com.cognizant.bid.cqrs.events.BidUpdateAmountdEvent;

public interface EventHandler {
    void on(BidAddEvent event);
    void on(BidUpdateAmountdEvent event);
}
