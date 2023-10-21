package br.com.toplibrary.domain.repository;

import br.com.toplibrary.domain.model.rental.Rental;
import br.com.toplibrary.domain.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface RentalRepository extends JpaRepository<Rental, UUID> {
    List<Rental> findByUser(User user);
}
