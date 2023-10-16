package br.com.toplibrary.domain.model.user;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Entity(name = "tb_user")
@Data
public class User {
    @Id @GeneratedValue(generator = "UUID")
    private UUID id;

    @Column(unique = true)
    private String email;

    @Column(unique = true)
    private String username;

    private String name;
    private String password;

    @Enumerated(EnumType.STRING)
    private UserRole role;

}
