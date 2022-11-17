package com.cognizant.bid.cqrs.infrastructure;

import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;

import com.cognizant.bid.cqrs.events.BidAddEvent;
import com.cognizant.bid.cqrs.events.BidUpdateAmountdEvent;

public interface EventConsumer {
    void consume(@Payload BidAddEvent event, Acknowledgment ack);
    void consume(@Payload BidUpdateAmountdEvent event, Acknowledgment ack);
}
