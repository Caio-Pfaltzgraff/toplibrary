package br.com.toplibrary.domain.repository;

import br.com.toplibrary.domain.model.book.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BookRepository extends JpaRepository<Book, UUID> {
}
