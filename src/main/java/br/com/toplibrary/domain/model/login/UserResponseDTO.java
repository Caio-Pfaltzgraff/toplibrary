package br.com.toplibrary.domain.model.login;

import br.com.toplibrary.domain.model.user.User;

import java.util.UUID;

public record UserResponseDTO(UUID id, String name, String username) {
    public UserResponseDTO(User user) {
        this(user.getId(), user.getName(), user.getUsername());
    }
}
