package com.cognizant.product.cqrs.config;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.axonframework.common.jpa.EntityManagerProvider;
import org.axonframework.eventsourcing.eventstore.EmbeddedEventStore;
import org.axonframework.eventsourcing.eventstore.EventStorageEngine;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.axonframework.extensions.mongo.DefaultMongoTemplate;
import org.axonframework.extensions.mongo.eventsourcing.eventstore.MongoEventStorageEngine;
import org.axonframework.serialization.json.JacksonSerializer;
import org.axonframework.spring.config.AxonConfiguration;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.boot.orm.jpa.hibernate.SpringImplicitNamingStrategy;
import org.springframework.boot.orm.jpa.hibernate.SpringPhysicalNamingStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.mongodb.MongoClient;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@EnableTransactionManagement
public class AxonEventStoreConfig {
    @Bean("axonMaster")
    @ConfigurationProperties("spring.datasource.axon-master")
    public DataSource axon() {
        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }

    @Bean(name="axonEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(
            EntityManagerFactoryBuilder builder, @Qualifier("axonMaster") DataSource axonMaster) {
        return builder
                .dataSource(axonMaster)
                .persistenceUnit("axonMaster")
                .properties(jpaProperties())
                .packages("org.axonframework.eventhandling.tokenstore",
                        "org.axonframework.modelling.saga.repository.jpa",
                        "org.axonframework.eventsourcing.eventstore.jpa")
                .build();
    }

    /**
     * For axon framework
     * @param entityManagerFactory
     * @return
     */
    @Bean
    public EntityManagerProvider entityManagerProvider(@Qualifier("axonEntityManagerFactory") LocalContainerEntityManagerFactoryBean entityManagerFactory) {
        return () -> entityManagerFactory.getObject().createEntityManager();
    }

    private Map<String, Object> jpaProperties() {
        Map<String, Object> props = new HashMap<>();
        props.put("hibernate.physical_naming_strategy", SpringPhysicalNamingStrategy.class.getName());
        props.put("hibernate.implicit_naming_strategy", SpringImplicitNamingStrategy.class.getName());
        props.put("hibernate.hbm2ddl.auto", "update");
        props.put("hibernate.show_sql", "true");
        return props;
    }
    
	@Bean
	public EmbeddedEventStore eventStore(EventStorageEngine storageEngine, AxonConfiguration configuration) {
		return EmbeddedEventStore.builder().storageEngine(storageEngine)
				.messageMonitor(configuration.messageMonitor(EventStore.class, "eventStore")).build();

	}

	@Bean
	public EventStorageEngine storageEngine(MongoClient client) {
		return MongoEventStorageEngine.builder()
				.mongoTemplate(DefaultMongoTemplate.builder().mongoDatabase(client).build())
				.eventSerializer(JacksonSerializer.defaultSerializer())
				.snapshotSerializer(JacksonSerializer.defaultSerializer()).build();
	}
	 
}

