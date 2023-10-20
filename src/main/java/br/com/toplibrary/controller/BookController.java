package br.com.toplibrary.controller;

import br.com.toplibrary.domain.model.book.Book;
import br.com.toplibrary.domain.model.book.BookDTO;
import br.com.toplibrary.domain.model.book.BookDTORead;
import br.com.toplibrary.service.BookService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @PostMapping
    public ResponseEntity<BookDTORead> create(@RequestBody @Valid BookDTO bookDto) {
        var book = bookService.getBookForDto(bookDto);
        var bookSaved = bookService.save(book);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(bookSaved.getId())
                .toUri();

        List<String> genreNames = new ArrayList<>();
        bookSaved.getGenres().forEach(g -> genreNames.add(g.getGenre().getName()));

        return ResponseEntity.created(location).body(new BookDTORead(bookSaved, genreNames));
    }

    @GetMapping
    public ResponseEntity<List<BookDTORead>> getBooks() {
        var books = bookService.findAll();
        List<BookDTORead> booksDto = new ArrayList<>();
        books.forEach(book ->{
            List<String> genreNames = new ArrayList<>();
            book.getGenres().forEach(g -> genreNames.add(g.getGenre().getName()));
            booksDto.add(new BookDTORead(book, genreNames));
        });
        return ResponseEntity.ok(booksDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookDTORead> getBook(@PathVariable UUID id) {
        var book = bookService.findById(id);
        List<String> genreNames = new ArrayList<>();
        book.getGenres().forEach(g -> genreNames.add(g.getGenre().getName()));
        return ResponseEntity.ok(new BookDTORead(book, genreNames));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookDTORead> update(@PathVariable UUID id, @RequestBody @Valid BookDTO bookDTO) {
        var book = bookService.getBookForDto(bookDTO);
        var bookUpdated = bookService.update(id, book);

        List<String> genreNames = new ArrayList<>();
        book.getGenres().forEach(g -> genreNames.add(g.getGenre().getName()));

        return ResponseEntity.ok(new BookDTORead(book, genreNames));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        bookService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
