package br.com.toplibrary.domain.model.user;

import java.util.UUID;

public record UserDTORead(
        UUID id,
        String username,
        String name,
        String email
) {
    public UserDTORead(User user) {
        this(user.getId(), user.getUsername(), user.getName(), user.getEmail());
    }
}
