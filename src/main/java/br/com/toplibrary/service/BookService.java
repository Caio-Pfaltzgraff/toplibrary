package br.com.toplibrary.service;

import br.com.toplibrary.domain.model.book.Book;
import br.com.toplibrary.domain.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;


    @Transactional
    public Book save(Book book) {
        return bookRepository.save(book);
    }
}
