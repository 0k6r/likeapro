package com.oku6er.likeAPro.service;


import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.containers.PostgreSQLContainer;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(initializers = AbstractIntegrationTest.Initializer.class)
@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = NONE)
@ComponentScan({
        "com.oku6er.likeapro.service.comment",
        "com.oku6er.likeapro.service.post",
        "com.oku6er.likeapro.service.tag"
})
abstract class AbstractIntegrationTest {

    private static PostgreSQLContainer postgreSQLContainer =
            new PostgreSQLContainer("postgres:11.1")
                    .withDatabaseName("likeapro_test")
                    .withUsername("sa")
                    .withPassword("sa");

    static {
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

                    //jpa:
                    //    open-in-view: false
                    //    hibernate:
                    //      ddl-auto: update
                    //    database-platform: org.hibernate.dialect.PostgreSQL9Dialect
                    //    properties:
                    //      default_schema: public
                    //      hibernate:
                    //        jdbc:
                    //          lob:
                    //            non_contextual_creation: true
            ).applyTo(configurableApplicationContext.getEnvironment());

            Flyway.configure()
                    .dataSource(postgreSQLContainer.getJdbcUrl(), postgreSQLContainer.getUsername(),
                            postgreSQLContainer.getPassword())
                    .load()
                    .migrate();
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
