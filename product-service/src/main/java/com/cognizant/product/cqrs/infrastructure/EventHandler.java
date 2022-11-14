package com.cognizant.product.cqrs.infrastructure;

import com.cognizant.product.cqrs.events.ProductAddEvent;
import com.cognizant.product.cqrs.events.ProductDeleteEvent;

public interface EventHandler {
    void on(ProductAddEvent event);
    void on(ProductDeleteEvent event);
}
