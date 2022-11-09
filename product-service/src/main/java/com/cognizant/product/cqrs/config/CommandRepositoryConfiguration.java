package com.cognizant.product.cqrs.config;

import org.axonframework.eventsourcing.eventstore.EmbeddedEventStore;
import org.axonframework.eventsourcing.eventstore.EventStorageEngine;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.axonframework.extensions.mongo.DefaultMongoTemplate;
import org.axonframework.extensions.mongo.eventsourcing.eventstore.MongoEventStorageEngine;
import org.axonframework.serialization.json.JacksonSerializer;
import org.axonframework.spring.config.AxonConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mongodb.client.MongoClient;

@Configuration
public class CommandRepositoryConfiguration {

	@Bean
	public EmbeddedEventStore eventStore(EventStorageEngine storageEngine,AxonConfiguration configuration) {
		return EmbeddedEventStore.builder()
				.storageEngine(storageEngine)
				.messageMonitor(configuration.messageMonitor(EventStore.class,"productEventStore"))
				.build();

	}

	@Bean
	public EventStorageEngine storageEngine(MongoClient client) {
		return MongoEventStorageEngine.builder()
				.mongoTemplate(DefaultMongoTemplate.builder().mongoDatabase(client).build())
				.eventSerializer(JacksonSerializer.defaultSerializer())
				.snapshotSerializer(JacksonSerializer.defaultSerializer())
				.build();
	}
}
