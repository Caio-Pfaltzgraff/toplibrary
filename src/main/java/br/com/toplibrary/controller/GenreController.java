package br.com.toplibrary.controller;

import br.com.toplibrary.domain.model.book.genre.Genre;
import br.com.toplibrary.service.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/genres")
public class GenreController {

    @Autowired
    private GenreService genreService;

    @PostMapping
    public ResponseEntity create(@RequestBody Genre genre) {
        return ResponseEntity.status(HttpStatus.CREATED).body(genreService.save(genre));
    }

    @GetMapping
    public ResponseEntity getGenres() {
        return ResponseEntity.status(HttpStatus.OK).body(genreService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity getGenre(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(genreService.findById(id));
    }

}
