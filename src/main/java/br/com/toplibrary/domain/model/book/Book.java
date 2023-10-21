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
    @ManyToOne(cascade = CascadeType.MERGE)
    private PublishingCompany publishingCompany;
    @ManyToOne(cascade = CascadeType.MERGE)
    private Author author;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<BookGenre> genres;

    public Book(String title, String isbn, Integer publicationYear, Integer quantity, PublishingCompany publishingCompany, Author author, List<BookGenre> genres) {
        this.title = title;
        this.isbn = isbn;
        this.publicationYear = publicationYear;
        this.quantity = quantity;
        this.publishingCompany = publishingCompany;
        this.author = author;
        this.genres = genres;
    }

    public void update(Book bookToUpdated) {
        if(bookToUpdated.title != null) {
            this.title = bookToUpdated.title;
        }
        if(bookToUpdated.publicationYear != null) {
            this.publicationYear = bookToUpdated.publicationYear;
        }
        if(bookToUpdated.quantity != null) {
            this.quantity = bookToUpdated.quantity;
        }
        if(bookToUpdated.publishingCompany != null) {
            this.publishingCompany = bookToUpdated.publishingCompany;
        }
        if(bookToUpdated.author != null) {
            this.author = bookToUpdated.author;
        }
        if(bookToUpdated.genres != null) {
            this.genres = bookToUpdated.genres;
        }
    }
}
