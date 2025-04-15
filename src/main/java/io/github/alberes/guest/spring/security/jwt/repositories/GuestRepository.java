package io.github.alberes.guest.spring.security.jwt.repositories;

import io.github.alberes.guest.spring.security.jwt.domains.Guest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GuestRepository  extends JpaRepository<Guest, String> {
}
