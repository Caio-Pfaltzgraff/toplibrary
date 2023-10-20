package br.com.toplibrary.domain.model.rental;

import br.com.toplibrary.domain.model.book.Book;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@NoArgsConstructor
public class RentalDTO {
    @NotNull
    private UUID idUser;
    @NotNull
    private List<UUID> idBooks;

    public RentalDTO(Rental rental) {
        idUser = rental.getUser().getId();
        List<UUID> books = new ArrayList<>(rental.getBooks().size());
        rental.getBooks().forEach(book -> books.add(book.getId()));
        idBooks = books;
    }
}
