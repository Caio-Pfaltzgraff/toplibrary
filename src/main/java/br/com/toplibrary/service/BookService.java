package br.com.toplibrary.service;

import br.com.toplibrary.domain.model.book.Book;
import br.com.toplibrary.domain.model.book.BookDTO;
import br.com.toplibrary.domain.model.book.author.Author;
import br.com.toplibrary.domain.model.book.bookGenre.BookGenre;
import br.com.toplibrary.domain.model.book.genre.Genre;
import br.com.toplibrary.domain.model.book.publishingCompany.PublishingCompany;
import br.com.toplibrary.domain.repository.BookRepository;
import br.com.toplibrary.infra.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class BookService implements CrudService<UUID, Book> {

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
        for (BookGenre bg : book.getGenres()) {
            bg.setBook(book);
            bookGenreService.save(bg);
        }
        return bookRepository.save(book);
    }

    @Transactional(readOnly = true)
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Book findById(UUID id) {
        return bookRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    @Transactional
    public Book update(UUID id, Book bookToUpdated) {
        var book = findById(id);
        book.update(bookToUpdated);
        return book;
    }

    @Transactional
    public void delete(UUID id) {
        var book = findById(id);
        bookRepository.delete(book);
    }

    @Transactional(readOnly = true)
    public Book getBookForDto(BookDTO bookDto) {
        var author = authorService.findById(bookDto.author());
        var publishingCompany = publishingCompanyService.findById(bookDto.publishingCompany());
        List<BookGenre> genres = new ArrayList<>();
        bookDto.genres().forEach(genre -> {
            var genreSaved = genreService.findById(genre);
            genres.add(new BookGenre(genreSaved));
        });

        return new Book(bookDto.title(), bookDto.isbn(), bookDto.publicationYear(),
                bookDto.quantity(), publishingCompany, author, genres);
    }
}
