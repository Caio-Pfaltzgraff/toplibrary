package br.com.toplibrary.service;

import br.com.toplibrary.domain.model.book.Book;
import br.com.toplibrary.domain.model.rental.Rental;
import br.com.toplibrary.domain.repository.RentalRepository;
import br.com.toplibrary.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class RentalService {

    @Autowired
    private RentalRepository rentalRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BookService bookService;

    @Transactional
    public Rental save(Rental rental) {
        System.out.println(rental);
        var user = userRepository.findByUsername(rental.getUser().getUsername());
        rental.setUser(user);

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
        return rentalRepository.findById(id).get();
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
}
