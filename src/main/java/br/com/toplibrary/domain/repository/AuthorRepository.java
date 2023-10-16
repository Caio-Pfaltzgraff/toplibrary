package br.com.toplibrary.domain.repository;

import br.com.toplibrary.domain.model.book.author.Author;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AuthorRepository extends JpaRepository<Author, UUID> {
}
