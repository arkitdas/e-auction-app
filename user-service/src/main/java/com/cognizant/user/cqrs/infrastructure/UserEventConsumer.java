package com.cognizant.user.cqrs.infrastructure;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import com.cognizant.user.cqrs.events.UserAddEvent;
import com.cognizant.user.cqrs.events.UserDeleteEvent;
import com.cognizant.user.exception.UserNotFoundException;

@Service
public class UserEventConsumer implements EventConsumer {
    private EventHandler eventHandler;
    
    UserEventConsumer(EventHandler eventHandler) {
    	this.eventHandler = eventHandler;
    }

    @KafkaListener(topics = "UserAddEvent", groupId = "${spring.kafka.consumer.group-id}")
    @Override
    public void consume(@Payload UserAddEvent event, Acknowledgment ack) {
        this.eventHandler.on(event);
        ack.acknowledge();
    }
    
    @KafkaListener(topics = "UserDeleteEvent", groupId = "${spring.kafka.consumer.group-id}")
    @Override
    public void consume(@Payload UserDeleteEvent event, Acknowledgment ack) throws UserNotFoundException {
        this.eventHandler.on(event);
        ack.acknowledge();
    }
}
