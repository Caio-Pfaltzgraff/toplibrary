package br.com.toplibrary.controller;

import br.com.toplibrary.domain.model.book.BookEntitiesDTO;
import br.com.toplibrary.domain.model.book.genre.Genre;
import br.com.toplibrary.service.GenreService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/genres")
@Tag(name = "Genre Controller", description = "REST-Controller para controle dos gêneros dos livros.")
public class GenreController {

    @Autowired
    private GenreService genreService;

    @PostMapping
    @Operation(summary = "Cria um gênero", description = "Cadastra um gênero no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Gênero cadastrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Campo inválido para criar gênero")
    })
    public ResponseEntity<BookEntitiesDTO> create(@RequestBody @Valid BookEntitiesDTO genreDto) {
        var genre = genreService.save(new Genre(genreDto.name()));
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(genre.getId())
                .toUri();

        return ResponseEntity.created(location).body(new BookEntitiesDTO(genre));
    }

    @GetMapping
    @Operation(summary = "Busca todos os gêneros", description = "Retorna uma lista com todos os gêneros registrados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operação realizada com sucesso")
    })
    public ResponseEntity<List<BookEntitiesDTO>> getGenres() {
        var listGenres = genreService.findAll();
        var listGenresDto = listGenres.stream().map(BookEntitiesDTO::new).toList();
        return ResponseEntity.ok(listGenresDto);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca gênero por id", description = "Retorna um gênero específico pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operação realizada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Gênero não encontrado")
    })
    public ResponseEntity<BookEntitiesDTO> getGenre(@PathVariable Long id) {
        var genre = genreService.findById(id);
        return ResponseEntity.ok(new BookEntitiesDTO(genre));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza dados de um gênero", description = "Atualiza dados de um gênero")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Gênero alterado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Campo inválido para alterar gênero"),
            @ApiResponse(responseCode = "404", description = "Gênero não encontrado"),
            @ApiResponse(responseCode = "422", description = "Campo de gênero já cadastrado")
    })
    public ResponseEntity<BookEntitiesDTO> update(@PathVariable Long id, @RequestBody @Valid BookEntitiesDTO genreToUpdated) {
        var genre = genreService.update(id, new Genre(genreToUpdated.name()));
        return ResponseEntity.ok(new BookEntitiesDTO(genre));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deleta gênero", description = "Deleta um gênero por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Gênero deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Gênero não encontrado")
    })
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        genreService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
