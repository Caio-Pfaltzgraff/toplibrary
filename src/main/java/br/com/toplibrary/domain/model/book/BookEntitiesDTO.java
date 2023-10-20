package br.com.toplibrary.domain.model.book;

import br.com.toplibrary.domain.model.book.author.Author;
import br.com.toplibrary.domain.model.book.genre.Genre;
import br.com.toplibrary.domain.model.book.publishingCompany.PublishingCompany;
import jakarta.validation.constraints.NotBlank;

public record BookEntitiesDTO(
        @NotBlank
        String name) {

    public BookEntitiesDTO(Author author) {
        this(author.getName());
    }

    public BookEntitiesDTO(Genre genre) {
        this(genre.getName());
    }

    public BookEntitiesDTO(PublishingCompany publishingCompany) {
        this(publishingCompany.getName());
    }
}
