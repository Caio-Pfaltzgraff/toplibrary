package br.com.toplibrary.domain.repository;

import br.com.toplibrary.domain.model.book.bookGenre.BookGenre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookGenreRepository extends JpaRepository<BookGenre, Long> {
}
