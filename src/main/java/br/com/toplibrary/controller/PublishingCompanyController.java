package br.com.toplibrary.controller;

import br.com.toplibrary.domain.model.book.publishingCompany.PublishingCompany;
import br.com.toplibrary.service.PublishingCompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/publishing-company")
public class PublishingCompanyController {

    @Autowired
    private PublishingCompanyService service;

    @PostMapping
    public ResponseEntity create(@RequestBody PublishingCompany publishingCompany) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(publishingCompany));
    }

    @GetMapping
    public ResponseEntity getPublishingCompanys() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity getPublishingCompany(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

}
