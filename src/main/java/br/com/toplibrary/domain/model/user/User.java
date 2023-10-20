package br.com.toplibrary.domain.model.user;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Entity(name = "tb_user")
@Data
@NoArgsConstructor
public class User implements UserDetails {
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

    public void update(User userToUpdate) {
        if(userToUpdate.email != null) {
            this.email = userToUpdate.email;
        }
        if(userToUpdate.username != null) {
            this.username = userToUpdate.username;
        }
        if(userToUpdate.name != null) {
            this.name = userToUpdate.name;
        }
        if(userToUpdate.password != null) {
            this.password = userToUpdate.password;
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(this.role == UserRole.ADMIN) return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_USER"));
        else return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
