package br.com.toplibrary.domain.model.user;

public record UserDtoRead(
        String username,
        String name,
        String email
) {
    public UserDtoRead(User user) {
        this(user.getUsername(), user.getName(), user.getEmail());
    }
}
