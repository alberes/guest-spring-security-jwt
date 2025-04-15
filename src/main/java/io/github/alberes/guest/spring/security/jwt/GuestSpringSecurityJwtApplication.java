package io.github.alberes.guest.spring.security.jwt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class GuestSpringSecurityJwtApplication {

	public static void main(String[] args) {
		SpringApplication.run(GuestSpringSecurityJwtApplication.class, args);
	}

}
