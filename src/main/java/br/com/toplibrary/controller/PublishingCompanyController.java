package br.com.toplibrary.controller;

import br.com.toplibrary.domain.model.book.BookEntitiesDTO;
import br.com.toplibrary.domain.model.book.author.Author;
import br.com.toplibrary.domain.model.book.publishingCompany.PublishingCompany;
import br.com.toplibrary.service.PublishingCompanyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/publishing-company")
@Tag(name = "Publishing Company Controller", description = "REST-Controller para controle das editoras.")
public class PublishingCompanyController {

    @Autowired
    private PublishingCompanyService service;

    @PostMapping
    @Operation(summary = "Cria uma editora", description = "Cadastra uma editora no banco")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Editora criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Campo inválido para criar editora")
    })
    public ResponseEntity<BookEntitiesDTO> create(@RequestBody @Valid BookEntitiesDTO publishingCompanyDto) {
        var publishingCompany = service.save(new PublishingCompany(publishingCompanyDto.name()));
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(publishingCompany.getId())
                .toUri();
        return ResponseEntity.created(location).body(new BookEntitiesDTO(publishingCompany));
    }

    @GetMapping
    @Operation(summary = "Busca todas as editoras", description = "Retorna uma lista com todas as editoras registradas.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operação realizada com sucesso")
    })
    public ResponseEntity<List<BookEntitiesDTO>> getPublishingCompanys() {
        var listPublishingCompany = service.findAll();
        var listPublishingCompanyDto = listPublishingCompany.stream().map(BookEntitiesDTO::new).toList();
        return ResponseEntity.ok(listPublishingCompanyDto);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca editora por id", description = "Retorna uma editoras específica por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operação realizada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Editora não encontrada")
    })
    public ResponseEntity<BookEntitiesDTO> getPublishingCompany(@PathVariable Long id) {
        var publishingCompany = service.findById(id);
        return ResponseEntity.ok(new BookEntitiesDTO(publishingCompany));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza dados de um usuário", description = "Atualiza dados de um usuário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Editora alterada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Campo inválid para alterar editora"),
            @ApiResponse(responseCode = "404", description = "Editora não encontrada")
    })
    public ResponseEntity<BookEntitiesDTO> update(@PathVariable Long id, @RequestBody @Valid BookEntitiesDTO publishingCompanyToUpdated) {
        var publishingCompany = service.update(id, new PublishingCompany(publishingCompanyToUpdated.name()));
        return ResponseEntity.ok(new BookEntitiesDTO(publishingCompany));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deleta uma editora", description = "Deleta uma editora pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Editora deletada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Editora não encontrada")
    })
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

}
