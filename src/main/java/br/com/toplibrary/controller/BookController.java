package br.com.toplibrary.controller;

import br.com.toplibrary.domain.model.book.BookDTO;
import br.com.toplibrary.domain.model.book.BookDTORead;
import br.com.toplibrary.service.BookService;
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
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/books")
@Tag(name = "Book Controller", description = "REST-Controller para controle dos livros.")
public class BookController {

    @Autowired
    private BookService bookService;

    @PostMapping
    @Operation(summary = "Cria um livro", description = "Cadastra um livro a partir do id de um usuário e ids de livros")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Livro cadastrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Campo inválido para criar livro"),
            @ApiResponse(responseCode = "404", description = "Campo de gênero, editora ou autor não encontrado"),
            @ApiResponse(responseCode = "422", description = "Livro com isbn já existente")
    })
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
    @Operation(summary = "Busca todos os livros", description = "Retorna uma lista com todos os livros registrados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operação realizada com sucesso")
    })
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
    @Operation(summary = "Busca livro por id", description = "Retorna um livro específico pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operação realizada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Livro não encontrado")
    })
    public ResponseEntity<BookDTORead> getBook(@PathVariable UUID id) {
        var book = bookService.findById(id);
        List<String> genreNames = new ArrayList<>();
        book.getGenres().forEach(g -> genreNames.add(g.getGenre().getName()));
        return ResponseEntity.ok(new BookDTORead(book, genreNames));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza dados de um livro", description = "Atualiza dados de um livro")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Livro alterado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Campo inválido para alterar livro"),
            @ApiResponse(responseCode = "404", description = "Livro não encontrado"),
            @ApiResponse(responseCode = "422", description = "Campo de livro já cadastrado")
    })
    public ResponseEntity<BookDTORead> update(@PathVariable UUID id, @RequestBody @Valid BookDTO bookDTO) {
        var book = bookService.getBookForDto(bookDTO);
        var bookUpdated = bookService.update(id, book);

        List<String> genreNames = new ArrayList<>();
        book.getGenres().forEach(g -> genreNames.add(g.getGenre().getName()));

        return ResponseEntity.ok(new BookDTORead(book, genreNames));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deleta um livro", description = "Deleta um livro pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Livro deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Livro não encontrado")
    })
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        bookService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
