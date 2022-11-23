package com.cognizant.bid.cqrs.infrastructure;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import com.cognizant.bid.cqrs.events.BidAddEvent;
import com.cognizant.bid.cqrs.events.BidUpdateAmountdEvent;

@Service
public class BidEventConsumer implements EventConsumer {
//    @Autowired
    private EventHandler eventHandler;
    
    BidEventConsumer(EventHandler eventHandler) {
    	this.eventHandler = eventHandler;
    }

    @KafkaListener(topics = "BidAddEvent", groupId = "${spring.kafka.consumer.group-id}")
    @Override
    public void consume(@Payload BidAddEvent event, Acknowledgment ack) {
        this.eventHandler.on(event);
        ack.acknowledge();
    }

    @KafkaListener(topics = "BidUpdateAmountdEvent", groupId = "${spring.kafka.consumer.group-id}")
    @Override
    public void consume(@Payload BidUpdateAmountdEvent event, Acknowledgment ack) {
        this.eventHandler.on(event);
        ack.acknowledge();
    }
}
