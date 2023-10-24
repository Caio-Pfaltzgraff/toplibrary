package br.com.toplibrary.domain.repository;

import br.com.toplibrary.domain.model.book.genre.Genre;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GenreRepository extends JpaRepository<Genre, Long> {
    @Override
    @Cacheable("genre")
    Optional<Genre> findById(Long aLong);

    @Override
    @Cacheable("genre")
    List<Genre> findAll();
}
