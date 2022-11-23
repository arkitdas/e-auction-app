package com.cognizant.user.cqrs.infrastructure;

import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;

import com.cognizant.user.cqrs.events.UserAddEvent;
import com.cognizant.user.cqrs.events.UserDeleteEvent;
import com.cognizant.user.exception.UserNotFoundException;

public interface EventConsumer {
    void consume(@Payload UserAddEvent event, Acknowledgment ack);

	void consume(@Payload UserDeleteEvent event, Acknowledgment ack) throws UserNotFoundException;
}
