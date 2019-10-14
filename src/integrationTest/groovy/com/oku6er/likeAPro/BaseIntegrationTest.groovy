package com.oku6er.likeAPro


import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.spock.Testcontainers
import spock.lang.Shared
import spock.lang.Specification

@Testcontainers
class BaseIntegrationTest extends Specification {

    @Shared
    PostgreSQLContainer postgreSQLContainer = CustomPostgreSQLContainer.getInstance()
}
