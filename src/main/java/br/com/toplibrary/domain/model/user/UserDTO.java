package br.com.toplibrary.domain.model.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserDTO(
        @NotBlank
        @Email
        String email,
        @NotBlank
        String username,
        @NotBlank
        String name,
        @NotBlank
        String password
) {
}
