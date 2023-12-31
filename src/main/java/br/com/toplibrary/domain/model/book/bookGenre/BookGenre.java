package br.com.toplibrary.domain.model.book.bookGenre;

import br.com.toplibrary.domain.model.book.Book;
import br.com.toplibrary.domain.model.book.genre.Genre;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "tb_book_genre")
@Data
@NoArgsConstructor
public class BookGenre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "tb_book_id")
    @JsonBackReference
    private Book book;
    @ManyToOne
    @JoinColumn(name = "tb_genre_id")
    private Genre genre;

    public BookGenre(Genre genre) {
        this.genre = genre;
    }
}
