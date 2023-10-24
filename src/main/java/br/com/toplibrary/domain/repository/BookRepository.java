package br.com.toplibrary.domain.repository;

import br.com.toplibrary.domain.model.book.Book;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BookRepository extends JpaRepository<Book, UUID> {
    @Override
    @Cacheable("book")
    Optional<Book> findById(UUID uuid);

    @Override
    @Cacheable("book")
    List<Book> findAll();
}
