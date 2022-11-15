package com.cognizant.buyer.cqrs.infrastructure;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import com.cognizant.buyer.cqrs.events.BuyerAddEvent;
import com.cognizant.buyer.cqrs.events.SellerDeleteEvent;

@Service
public class SellerEventConsumer implements EventConsumer {
    private EventHandler eventHandler;
    
    SellerEventConsumer(EventHandler eventHandler) {
    	this.eventHandler = eventHandler;
    }

    @KafkaListener(topics = "SellerAddEvent", groupId = "${spring.kafka.consumer.group-id}")
    @Override
    public void consume(@Payload BuyerAddEvent event, Acknowledgment ack) {
        this.eventHandler.on(event);
        ack.acknowledge();
    }

	/*
	 * @KafkaListener(topics = "SellerDeleteEvent", groupId =
	 * "${spring.kafka.consumer.group-id}")
	 * 
	 * @Override public void consume(@Payload SellerDeleteEvent event,
	 * Acknowledgment ack) { this.eventHandler.on(event); ack.acknowledge(); }
	 */
}
