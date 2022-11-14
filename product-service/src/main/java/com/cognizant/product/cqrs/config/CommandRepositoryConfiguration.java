package com.cognizant.product.cqrs.config;

import org.axonframework.eventsourcing.eventstore.EmbeddedEventStore;
import org.axonframework.eventsourcing.eventstore.EventStorageEngine;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.axonframework.extensions.mongo.DefaultMongoTemplate;
import org.axonframework.extensions.mongo.eventsourcing.eventstore.MongoEventStorageEngine;
import org.axonframework.spring.config.AxonConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

//@Configuration
public class CommandRepositoryConfiguration {

	/*
	 * @Bean public EmbeddedEventStore eventStore(EventStorageEngine
	 * storageEngine,AxonConfiguration configuration) { return
	 * EmbeddedEventStore.builder() .storageEngine(storageEngine)
	 * .messageMonitor(configuration.messageMonitor(EventStore.class,"eventStore"))
	 * .build();
	 * 
	 * }
	 * 
	 * @Bean public EventStorageEngine storageEngine(MongoClient client) { return
	 * MongoEventStorageEngine.builder()
	 * .mongoTemplate(DefaultMongoTemplate.builder().mongoDatabase(client).build())
	 * .eventSerializer(JacksonSerializer.defaultSerializer())
	 * .snapshotSerializer(JacksonSerializer.defaultSerializer()) .build(); }
	 */

	/*
	 * @Bean("mongoClient") public MongoClient mongoClient() { MongoClientURI uri =
	 * new
	 * MongoClientURI("mongodb://root:password@mongodb:27017/?authSource=admin");
	 * MongoClient mongoClient = new MongoClient(uri); return mongoClient;
	 * 
	 * }
	 * 
	 * @Bean public EventStorageEngine storageEngine(MongoClient mongoClient) {
	 * return MongoEventStorageEngine.builder()
	 * .mongoTemplate(DefaultMongoTemplate.builder().mongoDatabase(mongoClient).
	 * build()).build(); }
	 * 
	 * // The Event store `EmbeddedEventStore` delegates actual storage and
	 * retrieval // of events to an `EventStorageEngine`.
	 * 
	 * @Bean public EmbeddedEventStore eventStore(EventStorageEngine storageEngine,
	 * AxonConfiguration configuration) { return
	 * EmbeddedEventStore.builder().storageEngine(storageEngine)
	 * .messageMonitor(configuration.messageMonitor(EventStore.class,
	 * "eventStore")).build(); }
	 */
}
