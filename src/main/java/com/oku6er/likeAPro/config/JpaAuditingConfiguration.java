package com.oku6er.likeAPro.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Optional;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
public class JpaAuditingConfiguration {

    @Bean
    public AuditorAware<String> auditorProvider() {

          // TODO: if you are using spring security, you can get the currently logged username with following code segment.
          // SecurityContextHolder.getContext().getAuthentication().getTitle()
        return () -> Optional.of("oku6er");
    }
}
