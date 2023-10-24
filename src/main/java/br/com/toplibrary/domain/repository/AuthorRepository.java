package br.com.toplibrary.domain.repository;

import br.com.toplibrary.domain.model.book.author.Author;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository extends JpaRepository<Author, Long> {
    @Override
    @Cacheable("author")
    Optional<Author> findById(Long aLong);

    @Override
    @Cacheable("author")
    List<Author> findAll();
}
