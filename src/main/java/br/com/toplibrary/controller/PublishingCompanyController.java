package br.com.toplibrary.controller;

import br.com.toplibrary.domain.model.book.BookEntitiesDTO;
import br.com.toplibrary.domain.model.book.author.Author;
import br.com.toplibrary.domain.model.book.publishingCompany.PublishingCompany;
import br.com.toplibrary.service.PublishingCompanyService;
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
public class PublishingCompanyController {

    @Autowired
    private PublishingCompanyService service;

    @PostMapping
    public ResponseEntity<BookEntitiesDTO> create(@RequestBody @Valid BookEntitiesDTO publishingCompanyDto) {
        var publishingCompany = service.save(new PublishingCompany(publishingCompanyDto.name()));
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(publishingCompany.getId())
                .toUri();
        return ResponseEntity.created(location).body(new BookEntitiesDTO(publishingCompany));
    }

    @GetMapping
    public ResponseEntity<List<BookEntitiesDTO>> getPublishingCompanys() {
        var listPublishingCompany = service.findAll();
        var listPublishingCompanyDto = listPublishingCompany.stream().map(BookEntitiesDTO::new).toList();
        return ResponseEntity.ok(listPublishingCompanyDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookEntitiesDTO> getPublishingCompany(@PathVariable Long id) {
        var publishingCompany = service.findById(id);
        return ResponseEntity.ok(new BookEntitiesDTO(publishingCompany));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookEntitiesDTO> update(@PathVariable Long id, @RequestBody @Valid BookEntitiesDTO publishingCompanyToUpdated) {
        var publishingCompany = service.update(id, new PublishingCompany(publishingCompanyToUpdated.name()));
        return ResponseEntity.ok(new BookEntitiesDTO(publishingCompany));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

}
