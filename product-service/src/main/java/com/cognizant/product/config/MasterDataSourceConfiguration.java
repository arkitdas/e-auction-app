package com.cognizant.product.config;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.boot.orm.jpa.hibernate.SpringImplicitNamingStrategy;
import org.springframework.boot.orm.jpa.hibernate.SpringPhysicalNamingStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariDataSource;


/**
 * Datasource master
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = {"com.cognizant.product.repository"},
        entityManagerFactoryRef = "projectionMasterEntityManager")
public class MasterDataSourceConfiguration {
    @Primary
    @Bean("projectionMaster")
    @ConfigurationProperties("spring.datasource.projection-master")
    public DataSource master() {
        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }

    @Primary
    @Bean(name = "projectionMasterEntityManager")
    public LocalContainerEntityManagerFactoryBean projectionMasterEntityManager(EntityManagerFactoryBuilder builder) {
        return  builder.dataSource(master())
                .persistenceUnit("projectionMaster")
                .properties(jpaProperties())
                .packages("com.cognizant.product.repository")
                .build();
    }

    private Map<String, Object> jpaProperties() {
        Map<String, Object> props = new HashMap<>();
        props.put("hibernate.physical_naming_strategy", SpringPhysicalNamingStrategy.class.getName());
        props.put("hibernate.implicit_naming_strategy", SpringImplicitNamingStrategy.class.getName());
        props.put("hibernate.hbm2ddl.auto", "update");
        props.put("hibernate.show_sql", "true");
        props.put("spring.jpa.database-platform", "org.hibernate.dialect.H2Dialect");
        return props;
    }
}
