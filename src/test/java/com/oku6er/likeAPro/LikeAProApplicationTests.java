package com.oku6er.likeAPro;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(
        classes = LikeAProApplicationTests.TestConfiguration.class
)

class LikeAProApplicationTests {

    @Test
    void contextLoads() {
    }

    @EnableAutoConfiguration(exclude =
            {
                    DataSourceAutoConfiguration.class,
                    HibernateJpaAutoConfiguration.class,
                    FlywayAutoConfiguration.class
            }
    )
    @Configuration
    static class TestConfiguration {
    }
}
