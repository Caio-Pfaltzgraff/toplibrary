package br.com.toplibrary.controller;

import br.com.toplibrary.domain.model.rental.RentalDTO;
import br.com.toplibrary.domain.model.rental.RentalDTORead;
import br.com.toplibrary.service.RentalService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/rentals")
public class RentalController {

    @Autowired
    private RentalService rentalService;

    @PostMapping
    public ResponseEntity<RentalDTORead> create(@RequestBody @Valid RentalDTO rentalDto) {
        var rental = rentalService.getRentalForDto(rentalDto);
        rentalService.save(rental);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(rental.getId())
                .toUri();
        return ResponseEntity.created(location).body(new RentalDTORead(rental));
    }

    @GetMapping
    public ResponseEntity<List<RentalDTORead>> getRentals() {
        var listRentals = rentalService.findAll();
        var listRentalsDto = listRentals.stream().map(RentalDTORead::new).toList();
        return ResponseEntity.ok(listRentalsDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RentalDTORead> getRental(@PathVariable UUID id) {
        var rental = rentalService.findById(id);
        return ResponseEntity.ok(new RentalDTORead(rental));
    }

    @GetMapping("/devolution/{id}")
    public ResponseEntity<Map<String, String>> rentRefund(@PathVariable UUID id) {
        return ResponseEntity.ok().body(rentalService.rentRefund(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RentalDTORead> update(@PathVariable UUID id, @RequestBody RentalDTO rentalToUpdated) {
        var dtoToRental = rentalService.getRentalForDto(rentalToUpdated);
        var rental = rentalService.update(id, dtoToRental);
        return ResponseEntity.ok(new RentalDTORead(rental));
    }

}
