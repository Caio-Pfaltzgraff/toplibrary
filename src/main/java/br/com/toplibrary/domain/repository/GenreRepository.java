package br.com.toplibrary.domain.repository;

import br.com.toplibrary.domain.model.book.genre.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenreRepository extends JpaRepository<Genre, Long> {
}
