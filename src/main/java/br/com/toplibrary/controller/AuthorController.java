package br.com.toplibrary.controller;

import br.com.toplibrary.domain.model.book.BookEntitiesDTO;
import br.com.toplibrary.domain.model.book.author.Author;
import br.com.toplibrary.service.AuthorService;
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
@RequestMapping("/authors")
@Tag(name = "Author Controller", description = "REST-Controller para controle dos autores.")
public class AuthorController {

    @Autowired
    private AuthorService authorService;

    @PostMapping
    @Operation(summary = "Cria um autor", description = "Cadastra um autor no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "autor cadastrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Campo inválido para criar autor")
    })
    public ResponseEntity<BookEntitiesDTO> create(@RequestBody @Valid BookEntitiesDTO authorDto) {
        var author = authorService.save(new Author(authorDto.name()));
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(author.getId())
                .toUri();

        return ResponseEntity.created(location).body(new BookEntitiesDTO(author));
    }

    @GetMapping
    @Operation(summary = "Busca todos os autores", description = "Retorna uma lista com todos os autores registrados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operação realizada com sucesso")
    })
    public ResponseEntity<List<BookEntitiesDTO>> getAuthors() {
        var listAuthors = authorService.findAll();
        var listAuthorsDto = listAuthors.stream().map(BookEntitiesDTO::new).toList();
        return ResponseEntity.ok(listAuthorsDto);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca autor por id", description = "Retorna um autor específico pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operação realizada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Autor não encontrado")
    })
    public ResponseEntity<BookEntitiesDTO> getAuthor(@PathVariable Long id) {
        var author = authorService.findById(id);
        return ResponseEntity.ok(new BookEntitiesDTO(author));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza dados de um autor", description = "Atualiza dados de um autor")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Autor alterado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Campo inválido para alterar autor"),
            @ApiResponse(responseCode = "404", description = "Autor não encontrado")
    })
    public ResponseEntity<BookEntitiesDTO> update(@PathVariable Long id, @RequestBody @Valid BookEntitiesDTO authorToUpdated) {
        var author = authorService.update(id, new Author(authorToUpdated.name()));
        return ResponseEntity.ok(new BookEntitiesDTO(author));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deleta um autor", description = "Deleta um autor pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Autor deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Autor não encontrado")
    })
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        authorService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
