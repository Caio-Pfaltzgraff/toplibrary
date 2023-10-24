package br.com.toplibrary.domain.repository;

import br.com.toplibrary.domain.model.rental.Rental;
import br.com.toplibrary.domain.model.user.User;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RentalRepository extends JpaRepository<Rental, UUID> {
    @Cacheable("rentals")
    List<Rental> findByUser(User user);
    @Override
    @Cacheable("rentals")
    Optional<Rental> findById(UUID uuid);

    @Override
    @Cacheable("rentals")
    List<Rental> findAll();

}
