package br.com.toplibrary.domain.model.user;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.util.UUID;

@Entity(name = "tb_user")
@Data
@NoArgsConstructor
public class User {
    @Id @GeneratedValue(generator = "UUID")
    private UUID id;

    @Column(unique = true)
    private String email;

    @Column(unique = true)
    @Length(min = 3)
    private String username;

    private String name;
    private String password;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    public User(String email, String username, String name, String password) {
        this.email = email;
        this.username = username;
        this.name = name;
        this.password = password;
        this.role = UserRole.USER;
    }

    public User(String email, String username, String name, String password, UserRole userRole) {
        this.email = email;
        this.username = username;
        this.name = name;
        this.password = password;
        this.role = userRole;
    }

    public UserDtoRead toDto() {
        return new UserDtoRead(this.username, this.name, this.email);
    }
}
