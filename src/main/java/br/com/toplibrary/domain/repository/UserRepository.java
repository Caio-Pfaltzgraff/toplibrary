package br.com.toplibrary.domain.repository;

import br.com.toplibrary.domain.model.user.User;
import br.com.toplibrary.infra.exception.NotFoundException;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    @Cacheable("users")
    User findByUsername(String username) throws NotFoundException;

    @Cacheable("users")
    boolean existsByUsername(String username);

    @Override
    @Cacheable("users")
    List<User> findAll();

    @Override
    @Cacheable("users")
    Optional<User> findById(UUID uuid);

    @Cacheable("users")
    List<User> findAllByAtivoTrue();
}
