package br.com.toplibrary.controller;

import br.com.toplibrary.domain.model.book.BookEntitiesDTO;
import br.com.toplibrary.domain.model.book.genre.Genre;
import br.com.toplibrary.service.GenreService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/genres")
public class GenreController {

    @Autowired
    private GenreService genreService;

    @PostMapping
    public ResponseEntity<BookEntitiesDTO> create(@RequestBody @Valid BookEntitiesDTO genreDto) {
        var genre = genreService.save(new Genre(genreDto.name()));
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(genre.getId())
                .toUri();

        return ResponseEntity.created(location).body(new BookEntitiesDTO(genre));
    }

    @GetMapping
    public ResponseEntity<List<BookEntitiesDTO>> getGenres() {
        var listGenres = genreService.findAll();
        var listGenresDto = listGenres.stream().map(BookEntitiesDTO::new).toList();
        return ResponseEntity.ok(listGenresDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookEntitiesDTO> getGenre(@PathVariable Long id) {
        var genre = genreService.findById(id);
        return ResponseEntity.ok(new BookEntitiesDTO(genre));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookEntitiesDTO> update(@PathVariable Long id, @RequestBody @Valid BookEntitiesDTO genreToUpdated) {
        var genre = genreService.update(id, new Genre(genreToUpdated.name()));
        return ResponseEntity.ok(new BookEntitiesDTO(genre));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        genreService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
