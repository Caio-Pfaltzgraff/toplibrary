package br.com.toplibrary.domain.repository;

import br.com.toplibrary.domain.model.user.User;
import br.com.toplibrary.infra.exception.NotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    User findByUsername(String username) throws NotFoundException;
}
