package com.oku6er.likeAPro

import groovy.util.logging.Slf4j
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.containers.output.Slf4jLogConsumer

@Slf4j
class CustomPostgreSQLContainer extends PostgreSQLContainer<CustomPostgreSQLContainer> {
    private static final String IMAGE_VERSION = "postgres:11.4"
    private static CustomPostgreSQLContainer container

    CustomPostgreSQLContainer() {
        super(IMAGE_VERSION)
    }

    static CustomPostgreSQLContainer getInstance() {
        if (container == null) {
            container = new CustomPostgreSQLContainer()
        }
        return container
    }

    @Override
    void start() {
        container.addEnv("MAX_HEAP_SIZE", "512M")
        container.addEnv("HEAP_NEWSIZE", "512M")
        container.withLogConsumer(new Slf4jLogConsumer(log))
        super.start()
        System.setProperty("spring.datasource.driver-class-title", container.getDriverClassName())
        System.setProperty("spring.datasource.url", container.getJdbcUrl())
        System.setProperty("spring.datasource.username", container.getUsername())
        System.setProperty("spring.datasource.password", container.getPassword())
        System.setProperty("spring.datasource.initialization-mode", "always")
    }
    @Override
    void stop() {
        //do nothing, JVM handles shut down
    }

    @Override
    void close() {
        //do nothing, JVM handles shut down
    }
}