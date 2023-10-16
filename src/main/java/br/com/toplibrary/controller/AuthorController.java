package br.com.toplibrary.controller;

import br.com.toplibrary.domain.model.book.author.Author;
import br.com.toplibrary.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/authors")
public class AuthorController {

    @Autowired
    private AuthorService authorService;

    @PostMapping
    public ResponseEntity create(@RequestBody Author author) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authorService.save(author));
    }

    @GetMapping
    public ResponseEntity getAuthors() {
        List<Author> listAuthors = authorService.findAll();
        return ResponseEntity.ok(listAuthors);
    }

    @GetMapping("/{id}")
    public ResponseEntity getAuthor(@PathVariable Long id) {
        Author author = authorService.findById(id);
        return ResponseEntity.ok(author);
    }

}
