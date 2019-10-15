package com.oku6er.likeAPro

import org.springframework.test.context.ActiveProfiles
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.spock.Testcontainers
import spock.lang.Shared
import spock.lang.Specification

@Testcontainers
@ActiveProfiles('integration-test')
class BaseIntegrationTest extends Specification {

    @Shared
    PostgreSQLContainer postgreSQLContainer = CustomPostgreSQLContainer.getInstance()
}
