package com.oku6er.likeAPro.service;


import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;

import java.util.Properties;

import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;


@ContextConfiguration(initializers = AbstractIntegrationTest.Initializer.class)
@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = NONE)
@ComponentScan({"com.oku6er.likeapro.service"})
public abstract class AbstractIntegrationTest {

    @Container
    public static PostgreSQLContainer postgreSQLContainer =
            new PostgreSQLContainer("postgres:11.1")
                    .withDatabaseName("likeapro_test")
                    .withUsername("sa")
                    .withPassword("sa");

    public static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        @Override
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            ConfigurableEnvironment environment = configurableApplicationContext.getEnvironment();
            Properties props = new Properties();

            props.put("spring.datasource.driver-class-name", postgreSQLContainer.getDriverClassName());
            props.put("spring.datasource.url", postgreSQLContainer.getJdbcUrl());
            props.put("spring.datasource.username", postgreSQLContainer.getUsername());
            props.put("spring.datasource.password", postgreSQLContainer.getPassword());

            environment.getPropertySources().addFirst(new PropertiesPropertySource("myTestDBProps", props));
            configurableApplicationContext.setEnvironment(environment);
        }
    }
}
