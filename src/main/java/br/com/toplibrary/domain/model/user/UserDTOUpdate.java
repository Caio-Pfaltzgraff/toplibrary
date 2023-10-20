package br.com.toplibrary.domain.model.user;

public record UserDTOUpdate(
        String email,
        String username,
        String name,
        String password
) {
}
