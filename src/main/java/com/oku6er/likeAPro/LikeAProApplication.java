package com.oku6er.likeAPro;

import com.oku6er.likeAPro.model.Post;
import com.oku6er.likeAPro.repository.PostRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

@SpringBootApplication
public class LikeAProApplication {

	public static void main(String[] args) {
		SpringApplication.run(LikeAProApplication.class, args);
	}

	@Bean
	public CommandLineRunner setup(PostRepository postRepository) {
		return (args) -> {
			postRepository.save(new Post(1L,
					"About Java",
					"Post about Java", "programming, java", "",
					LocalDateTime.now()));
			postRepository.save(new Post(2L,
					"About .NET",
					"Post about .NET", "programming, net", "",
					LocalDateTime.now()));
			postRepository.save(new Post(3L,
					"How to use Elastic",
					"Don't use elastic", "programming", "",
					LocalDateTime.now()));
			postRepository.save(new Post(4L,
					"Postgresql",
					"My favorite database", "database", "how about MySQL, love u posts",
					LocalDateTime.now()));
		};
	}
}
