package com.oku6er.likeAPro

import groovy.util.logging.Slf4j
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.context.annotation.ComponentScan
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.spock.Testcontainers
import spock.lang.Shared
import spock.lang.Specification

import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE

@Testcontainers
@DataJpaTest
@AutoConfigureTestDatabase(replace = NONE)
@ComponentScan(basePackages = 'com.oku6er.likeapro.service')
class BaseIntegrationTest extends Specification {

    @Shared
    PostgreSQLContainer postgreSQLContainer = CustomPostgreSQLContainer.getInstance()
//            .withDatabaseName("likeapro_test")
//            .withUsername("sa")
//            .withPassword("sa")
//            .addEnv("MAX_HEAP_SIZE", "512M")
//            .addEnv("HEAP_NEWSIZE", "512M")
//            .withLogConsumer new Slf4jLogConsumer(log)
//            .start()

//
//    static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
//        @Override
//        void initialize(ConfigurableApplicationContext configurableApplicationContext) {
//            TestPropertyValues.of(
//                    "spring.datasource.driver-class-title${postgreSQLContainer.getDriverClassName()}",
//                    "spring.datasource.url=${postgreSQLContainer.getJdbcUrl()}",
//                    "spring.datasource.username=${postgreSQLContainer.getUsername()}",
//                    "spring.datasource.password=${postgreSQLContainer.getPassword()}",
//                    "spring.datasource.initialization-mode=always"
//            ).applyTo(configurableApplicationContext.getEnvironment())
//        }
//    }
//    def "container started"() {
//        given: "a jdbc connection"
//        HikariConfig hikariConfig = new HikariConfig()
//        hikariConfig.setJdbcUrl(postgreSQLContainer.jdbcUrl)
//        hikariConfig.setUsername("sa")
//        hikariConfig.setPassword("sa")
//        HikariDataSource ds = new HikariDataSource(hikariConfig)
//
//        when: "querying the database"
//        Statement statement = ds.getConnection().createStatement()
//        statement.execute("SELECT 1")
//        ResultSet resultSet = statement.getResultSet()
//        resultSet.next()
//
//        then: "result is returned"
//        int resultSetInt = resultSet.getInt(1)
//        resultSetInt == 1
//    }


//    static {
//        postgreSQLContainer = new PostgreSQLContainer("postgres:11.1")
//                .withDatabaseName("likeapro_test")
//                .withUsername("sa").withPassword("sa")
//        postgreSQLContainer.addEnv("MAX_HEAP_SIZE", "512M")
//        postgreSQLContainer.addEnv("HEAP_NEWSIZE", "512M")
//        postgreSQLContainer.withLogConsumer(new Slf4jLogConsumer(log))
//        postgreSQLContainer.start()
//    }

}
