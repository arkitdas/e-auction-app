package com.cognizant.cqrs.core.handlers;

import com.cognizant.cqrs.core.aggregate.AggregateRoot;

public interface EventSourcingHandler<T> {
    void save(AggregateRoot aggregate);
    T getById(String id);
    void republishEvents();
}
