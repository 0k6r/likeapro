package com.oku6er.likeAPro


import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.context.annotation.ComponentScan
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.transaction.annotation.EnableTransactionManagement
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.spock.Testcontainers
import spock.lang.Shared
import spock.lang.Specification

import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE

@Testcontainers
@DataJpaTest
@AutoConfigureTestDatabase(replace = NONE)
@ComponentScan(basePackages = 'com.oku6er.likeapro.service')
//@EnableTransactionManagement
//@EnableJpaRepositories(basePackages =
//        "com.oku6er.likeAPro.repository"
//)
class BaseIntegrationTest extends Specification {

    @Shared
    PostgreSQLContainer postgreSQLContainer = CustomPostgreSQLContainer.getInstance()

    def "test container"() {
        when: 'call container url'
        then: 'return url'
        postgreSQLContainer.getJdbcUrl() != null
    }
}
