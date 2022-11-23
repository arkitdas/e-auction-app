package com.cognizant.product.cqrs.infrastructure;

import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;

import com.cognizant.product.cqrs.events.ProductAddEvent;
import com.cognizant.product.cqrs.events.ProductDeleteEvent;

public interface EventConsumer {
    void consume(@Payload ProductAddEvent event, Acknowledgment ack);
    void consume(@Payload ProductDeleteEvent event, Acknowledgment ack);
}
