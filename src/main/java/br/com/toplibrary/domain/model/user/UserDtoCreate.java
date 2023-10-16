package br.com.toplibrary.domain.model.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UserDtoCreate(
        @NotBlank(message = "Email é obrigatório.")
        @Email(message = "Digite um e-mail válido.")
        String email,
        @NotBlank(message = "Nome de usuário é obrigatório.")
        String username,
        @NotBlank(message = "Nome é obrigatório.")
        String name,
        @NotBlank(message = "Senha é obrigatória.")
        String password
) {
}
