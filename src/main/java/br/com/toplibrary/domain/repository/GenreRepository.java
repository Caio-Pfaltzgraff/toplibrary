package br.com.toplibrary.domain.repository;

import br.com.toplibrary.domain.model.book.genre.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface GenreRepository extends JpaRepository<Genre, UUID> {
}
