package com.oku6er.likeAPro;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@EnableAutoConfiguration
@AutoConfigureTestDatabase(replace = Replace.NONE)
class LikeAProApplicationTests {

    @Test
    void contextLoads() {
    }
}
