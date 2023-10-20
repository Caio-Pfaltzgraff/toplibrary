package br.com.toplibrary.domain.repository;

import br.com.toplibrary.domain.model.rental.Rental;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface RentalRepository extends JpaRepository<Rental, UUID> {
//    @Query("SELECT * FROM Rental as r WHERE r.user_id = :userId")
//    List<Rental> findAllByIdUser(UUID userId);
}
