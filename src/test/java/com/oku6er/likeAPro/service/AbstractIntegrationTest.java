package com.oku6er.likeAPro.service;


import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.Extension;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.output.Slf4jLogConsumer;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;


@RunWith(SpringRunner.class)
@ContextConfiguration(initializers = AbstractIntegrationTest.Initializer.class)
@DataJpaTest
@EnableTransactionManagement
@AutoConfigureTestDatabase(replace = NONE)
@ComponentScan("com.oku6er.likeapro.service")
@Slf4j
abstract class AbstractIntegrationTest implements Extension {

    private static final PostgreSQLContainer postgreSQLContainer;

    static {
        postgreSQLContainer = new PostgreSQLContainer("postgres:11.1").withDatabaseName("likeapro_test")
                .withUsername("sa").withPassword("sa");
        postgreSQLContainer.addEnv("MAX_HEAP_SIZE", "512M");
        postgreSQLContainer.addEnv("HEAP_NEWSIZE", "512M");
        postgreSQLContainer.withLogConsumer(new Slf4jLogConsumer(log));
        postgreSQLContainer.start();
    }

    public static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        @Override
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            TestPropertyValues.of(
                    "spring.datasource.driver-class-title" + postgreSQLContainer.getDriverClassName(),
                    "spring.datasource.url=" + postgreSQLContainer.getJdbcUrl(),
                    "spring.datasource.username=" + postgreSQLContainer.getUsername(),
                    "spring.datasource.password=" + postgreSQLContainer.getPassword()

            ).applyTo(configurableApplicationContext.getEnvironment());
        }
    }

    @Test
    @DisplayName("Postgres container is running")
    void test() {
        System.out.printf("postgres db running, db-title: '%s', user: '%s', jdbc-url: '%s'%n ",
                postgreSQLContainer.getDatabaseName(),
                postgreSQLContainer.getUsername(),
                postgreSQLContainer.getJdbcUrl());
        assertTrue(postgreSQLContainer.isRunning());
    }
}
