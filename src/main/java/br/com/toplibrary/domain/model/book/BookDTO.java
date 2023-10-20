package br.com.toplibrary.domain.model.book;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record BookDTO(
        @NotBlank
        String title,
        @NotNull
        String isbn,
        Integer publicationYear,
        @NotNull
        Integer quantity,
        @NotNull
        Long publishingCompany,
        @NotNull
        Long author,
        @NotNull
        List<Long> genres
) {
}
