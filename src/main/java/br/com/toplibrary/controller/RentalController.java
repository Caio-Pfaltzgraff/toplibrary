package br.com.toplibrary.controller;

import br.com.toplibrary.domain.model.rental.Rental;
import br.com.toplibrary.service.RentalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/rentals")
public class RentalController {

    @Autowired
    private RentalService rentalService;

    @PostMapping
    public ResponseEntity create(@RequestBody Rental rental) {
        return ResponseEntity.status(HttpStatus.CREATED).body(rentalService.save(rental));
    }

    @GetMapping
    public ResponseEntity getRentals() {
        return ResponseEntity.ok(rentalService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity getRental(@PathVariable UUID id) {
        return ResponseEntity.ok(rentalService.findById(id));
    }

    @GetMapping("/devolution/{id}")
    public ResponseEntity rentRefund(@PathVariable UUID id) {
        return ResponseEntity.ok().body(rentalService.rentRefund(id));
    }

}
