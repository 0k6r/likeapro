package com.oku6er.likeAPro;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(properties = {
        "spring.flyway.enabled=false"
})
//@EnableAutoConfiguration
class LikeAProApplicationTests {

    @Test
    void contextLoads() {
    }
}
