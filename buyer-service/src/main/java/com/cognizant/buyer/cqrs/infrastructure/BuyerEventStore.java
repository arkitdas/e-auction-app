package com.cognizant.buyer.cqrs.infrastructure;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.cognizant.cqrs.core.events.BaseEvent;
import com.cognizant.cqrs.core.events.EventModel;
import com.cognizant.cqrs.core.events.EventStore;
import com.cognizant.cqrs.core.exceptions.AggregateNotFoundException;
import com.cognizant.cqrs.core.exceptions.ConcurrencyException;
import com.cognizant.cqrs.core.producers.EventProducer;
import com.cognizant.buyer.cqrs.aggreagate.BuyerAggregate;
import com.cognizant.buyer.cqrs.events.EventStoreRepository;

@Service
public class BuyerEventStore implements EventStore {
    private EventProducer eventProducer;

    private EventStoreRepository eventStoreRepository;
    
    BuyerEventStore(EventProducer eventProducer, EventStoreRepository eventStoreRepository) {
    	this.eventProducer = eventProducer;
    	this.eventStoreRepository = eventStoreRepository;
    }

    @Override
    public void saveEvents(String aggregateId, Iterable<BaseEvent> events, int expectedVersion) {
        var eventStream = eventStoreRepository.findByAggregateIdentifier(aggregateId);
        /**
         * Event store version will default be -1
         */
        if (expectedVersion != -1 && eventStream.get(eventStream.size() - 1).getVersion() != expectedVersion) {
            throw new ConcurrencyException();
        }
        var version = expectedVersion;
        for (var event: events) {
           version++;
           event.setVersion(version);
           var eventModel = EventModel.builder()
                   .timeStamp(new Date())
                   .aggregateIdentifier(aggregateId)
                   .aggregateType(BuyerAggregate.class.getTypeName())
                   .version(version)
                   .eventType(event.getClass().getTypeName())
                   .eventData(event)
                   .build();
           var persistedEvent = eventStoreRepository.save(eventModel);
           if (!persistedEvent.getId().isEmpty()) {
               eventProducer.produce(event.getClass().getSimpleName(), event);
           }
        }
    }

    @Override
    public List<BaseEvent> getEvents(String aggregateId) {
        var eventStream = eventStoreRepository.findByAggregateIdentifier(aggregateId);
        if (eventStream == null || eventStream.isEmpty()) {
            throw new AggregateNotFoundException("Incorrect account ID provided!");
        }
        return eventStream.stream().map(x -> x.getEventData()).collect(Collectors.toList());
    }

    @Override
    public List<String> getAggregateIds() {
        var eventStream = eventStoreRepository.findAll();
        if (eventStream == null || eventStream.isEmpty()) {
            throw new IllegalStateException("Could not retrieve event stream from the event store!");
        }
        return eventStream.stream().map(EventModel::getAggregateIdentifier).distinct().collect(Collectors.toList());
    }
}
