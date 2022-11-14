package com.cognizant.cqrs.core.producers;

import com.cognizant.cqrs.core.events.BaseEvent;

public interface EventProducer {
    void produce(String topic, BaseEvent event);
}
