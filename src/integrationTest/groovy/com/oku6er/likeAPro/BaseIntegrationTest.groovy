package com.oku6er.likeAPro


import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.spock.Testcontainers
import spock.lang.Shared
import spock.lang.Specification

@Testcontainers
//@DataJpaTest
//@AutoConfigureTestDatabase(replace = NONE)

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
