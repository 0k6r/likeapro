package com.oku6er.likeAPro;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(
		classes = LikeAProApplicationTests.TestConfiguration.class
)
public class LikeAProApplicationTests {

	@Test
	public void contextLoads() {
	}

	@EnableAutoConfiguration
	@Configuration
	static class TestConfiguration {
	}
}
