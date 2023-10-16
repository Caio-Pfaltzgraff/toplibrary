package br.com.toplibrary.domain.model.book.bookGenre;

import br.com.toplibrary.domain.model.book.Book;
import br.com.toplibrary.domain.model.book.genre.Genre;
import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Entity(name = "tb_book_genre")
@Data
public class BookGenre {
    @Id @GeneratedValue(generator = "UUID")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "tb_book_id")
    private Book book;
    @ManyToOne
    @JoinColumn(name = "tb_genre_id")
    private Genre genre;
}
