package io.github.alberes.guest.spring.security.jwt.controllers.dto;

public record TokenDto(String token, Long expirationDate) {
}
