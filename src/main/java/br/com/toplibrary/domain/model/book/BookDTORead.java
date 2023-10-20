package br.com.toplibrary.domain.model.book;

import java.util.List;
import java.util.UUID;

public record BookDTORead(
        UUID id,
        String title,
        String isbn,
        Integer publicationYear,
        Integer quantity,
        String publishingCompany,
        String author,
        List<String> genres
) {
    public BookDTORead(Book book, List<String> genresNames) {
        this(book.getId(), book.getTitle(), book.getIsbn(), book.getPublicationYear(),
                book.getQuantity(), book.getPublishingCompany().getName(),
                book.getAuthor().getName(), genresNames
        );
    }
}
