package br.com.toplibrary.service;

import br.com.toplibrary.domain.model.book.bookGenre.BookGenre;
import br.com.toplibrary.domain.repository.BookGenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BookGenreService {
    @Autowired
    private BookGenreRepository bookGenreRepository;

    @Transactional
    @CacheEvict(value = "bookGenre", allEntries = true)
    public BookGenre save(BookGenre bookGenre) {
        return bookGenreRepository.save(bookGenre);
    }
}
