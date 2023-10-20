package br.com.toplibrary.domain.model.rental;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
public class RentalDTORead {
    private UUID id;
    private String emailUser;
    private String nameUser;
    private List<String> booksTitle;
    private LocalDateTime rentalDate;

    public RentalDTORead(Rental rental) {
        this.id = rental.getId();
        this.emailUser = rental.getUser().getEmail();
        this.nameUser = rental.getUser().getName();
        this.rentalDate =rental.getRentalDate();
        List<String> titles = new ArrayList<>();
        rental.getBooks().forEach(book -> titles.add(book.getTitle()));
        this.booksTitle = titles;
    }
}
