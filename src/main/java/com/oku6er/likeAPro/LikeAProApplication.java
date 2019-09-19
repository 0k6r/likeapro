package com.oku6er.likeAPro;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
//@EnableJpaRepositories({"com.oku6er.likeAPro.repository"})
@EnableTransactionManagement
public class LikeAProApplication extends SpringBootServletInitializer {
	public static void main(String[] args) {
		SpringApplication.run(LikeAProApplication.class, args);
	}
}
