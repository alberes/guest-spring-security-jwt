package io.github.alberes.guest.spring.security.jwt.controllers.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserDto(@NotBlank(message = "Obligatory field")
                      @Size(min = 4, max = 40, message = "Fill this field with size between 4 and 40")
                      String username,
                      @NotBlank(message = "Obligatory field")
                      @Size(min = 7, max = 100, message = "Fill this field with size between 7 and 100")
                      String password) {
}