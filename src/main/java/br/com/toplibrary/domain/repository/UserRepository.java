package br.com.toplibrary.domain.repository;

import br.com.toplibrary.domain.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
}
