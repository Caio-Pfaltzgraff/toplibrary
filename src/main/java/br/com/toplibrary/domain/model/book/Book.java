package br.com.toplibrary.domain.model.book;

import br.com.toplibrary.domain.model.book.author.Author;
import br.com.toplibrary.domain.model.book.bookGenre.BookGenre;
import br.com.toplibrary.domain.model.book.genre.Genre;
import br.com.toplibrary.domain.model.book.publishingCompany.PublishingCompany;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Entity(name = "tb_book")
@Data
public class Book {
    @Id @GeneratedValue(generator = "UUID")
    private UUID id;
    private String title;
    @Column(unique = true)
    private String isbn;
    private Integer publicationYear;
    private Integer quantity;
    @ManyToOne(cascade = CascadeType.ALL)
    private PublishingCompany publishingCompany;
    @ManyToOne(cascade = CascadeType.ALL)
    private Author author;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<BookGenre> genres;
}
