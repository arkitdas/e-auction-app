package com.cognizant.seller.cqrs.infrastructure;

import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;

import com.cognizant.seller.cqrs.events.SellerAddEvent;
import com.cognizant.seller.cqrs.events.SellerDeleteEvent;

public interface EventConsumer {
    void consume(@Payload SellerAddEvent event, Acknowledgment ack);
//    void consume(@Payload SellerDeleteEvent event, Acknowledgment ack);
}
