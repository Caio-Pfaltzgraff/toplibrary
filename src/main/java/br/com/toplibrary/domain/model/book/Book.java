package br.com.toplibrary.domain.model.book;

import br.com.toplibrary.domain.model.book.author.Author;
import br.com.toplibrary.domain.model.book.bookGenre.BookGenre;
import br.com.toplibrary.domain.model.book.publishingCompany.PublishingCompany;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Entity(name = "tb_book")
@Data
@NoArgsConstructor
public class Book {
    @Id @GeneratedValue(generator = "UUID")
    private UUID id;
    private String title;
    @Column(unique = true)
    private String isbn;
    private Integer publicationYear;
    private Integer quantity;
    @ManyToOne(cascade = CascadeType.PERSIST)
    private PublishingCompany publishingCompany;
    @ManyToOne(cascade = CascadeType.PERSIST)
    private Author author;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<BookGenre> genres;
}
