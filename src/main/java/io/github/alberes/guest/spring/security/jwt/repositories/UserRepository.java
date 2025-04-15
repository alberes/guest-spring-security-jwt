package io.github.alberes.guest.spring.security.jwt.repositories;

import io.github.alberes.guest.spring.security.jwt.domains.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

    public User findByUsername(String username);
}
