package com.cognizant.buyer.cqrs.infrastructure;

import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;

import com.cognizant.buyer.cqrs.events.BuyerAddEvent;
import com.cognizant.buyer.cqrs.events.SellerDeleteEvent;

public interface EventConsumer {
    void consume(@Payload BuyerAddEvent event, Acknowledgment ack);
//    void consume(@Payload SellerDeleteEvent event, Acknowledgment ack);
}
