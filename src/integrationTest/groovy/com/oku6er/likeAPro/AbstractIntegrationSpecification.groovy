package com.oku6er.likeAPro

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import groovy.util.logging.Slf4j
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.containers.output.Slf4jLogConsumer
import org.testcontainers.spock.Testcontainers
import spock.lang.Shared
import spock.lang.Specification

import java.sql.ResultSet
import java.sql.Statement

@Slf4j
@Testcontainers
class AbstractIntegrationSpecification extends Specification {

    @Shared
    PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer("postgres:11.1")
            .withDatabaseName("likeapro_test")
            .withUsername("sa")
            .withPassword("sa")
//            .addEnv("MAX_HEAP_SIZE", "512M")
//            .addEnv("HEAP_NEWSIZE", "512M" )
//            .withLogConsumer(new Slf4jLogConsumer(log))
//            .start()

    def "container started"() {
        given: "a jdbc connection"
        HikariConfig hikariConfig = new HikariConfig()
        hikariConfig.setJdbcUrl(postgreSQLContainer.jdbcUrl)
        hikariConfig.setUsername("sa")
        hikariConfig.setPassword("sa")
        HikariDataSource ds = new HikariDataSource(hikariConfig)

        when: "querying the database"
        Statement statement = ds.getConnection().createStatement()
        statement.execute("SELECT 1")
        ResultSet resultSet = statement.getResultSet()
        resultSet.next()

        then: "result is returned"
        int resultSetInt = resultSet.getInt(1)
        resultSetInt == 1
    }


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
