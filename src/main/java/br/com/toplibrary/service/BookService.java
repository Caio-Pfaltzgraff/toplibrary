package br.com.toplibrary.service;

import br.com.toplibrary.domain.model.book.Book;
import br.com.toplibrary.domain.model.book.author.Author;
import br.com.toplibrary.domain.model.book.bookGenre.BookGenre;
import br.com.toplibrary.domain.model.book.genre.Genre;
import br.com.toplibrary.domain.model.book.publishingCompany.PublishingCompany;
import br.com.toplibrary.domain.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private PublishingCompanyService publishingCompanyService;
    @Autowired
    private AuthorService authorService;
    @Autowired
    private BookGenreService bookGenreService;
    @Autowired
    private GenreService genreService;


    @Transactional
    public Book save(Book book) {
        //verificar publishing company
        if(book.getPublishingCompany().getId() == null) {
            publishingCompanyService.save(new PublishingCompany(book.getPublishingCompany().getName()));
        } else {
            var publishingCompany = publishingCompanyService.findById(book.getPublishingCompany().getId());
            book.setPublishingCompany(publishingCompany);
        }

        //verificar author
        if(book.getAuthor().getId() == null) {
            authorService.save(new Author(book.getAuthor().getName()));
        } else {
            var author = authorService.findById(book.getAuthor().getId());
            book.setAuthor(author);
        }

        //verificar gender
        for (BookGenre bg : book.getGenres()) {
            if(bg.getGenre().getId() == null) {
                var genre = genreService.save(new Genre(bg.getGenre().getName()));
                bg.setGenre(genre);
            }
            bg.setBook(book);
            bookGenreService.save(bg);
        }
        return bookRepository.save(book);

    }

    public List<Book> findAll() {
        return bookRepository.findAll();
    }
}
