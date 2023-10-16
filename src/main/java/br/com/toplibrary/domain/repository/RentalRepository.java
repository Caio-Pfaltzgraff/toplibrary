package br.com.toplibrary.domain.repository;

import br.com.toplibrary.domain.model.rental.Rental;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RentalRepository extends JpaRepository<Rental, UUID> {
}