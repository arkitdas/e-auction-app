package com.cognizant.product.cqrs.infrastructure;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import com.cognizant.product.cqrs.events.ProductAddEvent;
import com.cognizant.product.cqrs.events.ProductDeleteEvent;

@Service
public class ProductEventConsumer implements EventConsumer {
//    @Autowired
    private EventHandler eventHandler;
    
    ProductEventConsumer(EventHandler eventHandler) {
    	this.eventHandler = eventHandler;
    }

    @KafkaListener(topics = "ProductAddEvent", groupId = "${spring.kafka.consumer.group-id}")
    @Override
    public void consume(@Payload ProductAddEvent event, Acknowledgment ack) {
        this.eventHandler.on(event);
        ack.acknowledge();
    }

    @KafkaListener(topics = "ProductDeleteEvent", groupId = "${spring.kafka.consumer.group-id}")
    @Override
    public void consume(@Payload ProductDeleteEvent event, Acknowledgment ack) {
        this.eventHandler.on(event);
        ack.acknowledge();
    }
}
