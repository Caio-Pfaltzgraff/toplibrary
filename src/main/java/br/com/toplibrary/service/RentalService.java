package br.com.toplibrary.service;

import br.com.toplibrary.domain.model.book.Book;
import br.com.toplibrary.domain.model.rental.Rental;
import br.com.toplibrary.domain.model.rental.RentalDTO;
import br.com.toplibrary.domain.repository.RentalRepository;
import br.com.toplibrary.infra.exception.BusinessException;
import br.com.toplibrary.infra.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class RentalService implements CrudService<UUID, Rental>{

    @Autowired
    private RentalRepository rentalRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private BookService bookService;

    @Transactional
    public Rental save(Rental rental) {
        if(rental.getUser().getUsername() != null) {
            var user = userService.findByUsername(rental.getUser().getUsername());
            rental.setUser(user);
        } else if(rental.getUser().getId() != null) {
            var user = userService.findById(rental.getUser().getId());
            rental.setUser(user);
        } else {
            throw new BusinessException("O campo user é obrigatório ter um id ou username.");
        }

        List<Book> booksToSave = new ArrayList<>();
        for(Book book : rental.getBooks()) {
            var bookSaved = bookService.findById(book.getId());
            booksToSave.add(bookSaved);
        }
        rental.setBooks(booksToSave);
        return rentalRepository.save(rental);
    }

    @Transactional(readOnly = true)
    public List<Rental> findAll() {
        return rentalRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Rental findById(UUID id) {
        return rentalRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    @Transactional
    public Rental update(UUID id, Rental rentalToUpdated) {
        var rental = findById(id);
        rental.setUser(rentalToUpdated.getUser());
        rental.setBooks(rentalToUpdated.getBooks());
        return save(rental);
    }

    @Transactional
    public void delete(UUID id) {
        var rental = findById(id);
        rentalRepository.delete(rental);
    }

    @Transactional
    public Map<String, String> rentRefund(UUID id) {
        var rental = rentalRepository.getReferenceById(id);
        rental.setDevolutionDate(LocalDateTime.now());
        rentalRepository.save(rental);
        var message =  "Devolução feita na data "
                + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                + " ás " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm"))
                + "h pelo usuário " + rental.getUser().getName();
        return Map.of("message", message);
    }

    @Transactional(readOnly = true)
    public Rental getRentalForDto(RentalDTO rentalToUpdated) {
        var user = userService.findById(rentalToUpdated.getIdUser());
        List<Book> bookList = new ArrayList<>();
        rentalToUpdated.getIdBooks().forEach(id -> bookList.add(bookService.findById(id)));
        return new Rental(user, bookList);
    }
}
