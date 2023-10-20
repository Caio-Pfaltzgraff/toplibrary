package br.com.toplibrary.controller;

import br.com.toplibrary.domain.model.book.BookEntitiesDTO;
import br.com.toplibrary.domain.model.book.author.Author;
import br.com.toplibrary.service.AuthorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/authors")
public class AuthorController {

    @Autowired
    private AuthorService authorService;

    @PostMapping
    public ResponseEntity<BookEntitiesDTO> create(@RequestBody @Valid BookEntitiesDTO authorDto) {
        var author = authorService.save(new Author(authorDto.name()));
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(author.getId())
                .toUri();

        return ResponseEntity.created(location).body(new BookEntitiesDTO(author));
    }

    @GetMapping
    public ResponseEntity<List<BookEntitiesDTO>> getAuthors() {
        var listAuthors = authorService.findAll();
        var listAuthorsDto = listAuthors.stream().map(BookEntitiesDTO::new).toList();
        return ResponseEntity.ok(listAuthorsDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookEntitiesDTO> getAuthor(@PathVariable Long id) {
        var author = authorService.findById(id);
        return ResponseEntity.ok(new BookEntitiesDTO(author));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookEntitiesDTO> update(@PathVariable Long id, @RequestBody @Valid BookEntitiesDTO authorToUpdated) {
        var author = authorService.update(id, new Author(authorToUpdated.name()));
        return ResponseEntity.ok(new BookEntitiesDTO(author));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        authorService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
