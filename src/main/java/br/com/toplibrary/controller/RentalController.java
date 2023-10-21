package br.com.toplibrary.controller;

import br.com.toplibrary.domain.model.rental.RentalDTO;
import br.com.toplibrary.domain.model.rental.RentalDTORead;
import br.com.toplibrary.service.RentalService;
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
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/rentals")
@Tag(name = "Rental Controller", description = "REST-Controller para controle dos aluguéis.")
public class RentalController {

    @Autowired
    private RentalService rentalService;

    @PostMapping
    @Operation(summary = "Cria um aluguel", description = "Cadastra um aluguel a partir do id de um usuário e ids de livros")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Aluguel realizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Campo inválido para criar aluguel"),
            @ApiResponse(responseCode = "404", description = "Campo de usuário ou livro não encontrado")
    })
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
    @Operation(summary = "Busca todos os aluguéis do usuário", description = "Retorna uma lista com todos os aluguéis do usuário autenticado que foram registrados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operação realizada com sucesso")
    })
    public ResponseEntity<List<RentalDTORead>> getRentals() {
        var listRentals = rentalService.findAll();
        var listRentalsDto = listRentals.stream().map(RentalDTORead::new).toList();
        return ResponseEntity.ok(listRentalsDto);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca aluguel por id", description = "Retorna um aluguel específico pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operação realizada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Aluguel não encontrado")
    })
    public ResponseEntity<RentalDTORead> getRental(@PathVariable UUID id) {
        var rental = rentalService.findById(id);
        return ResponseEntity.ok(new RentalDTORead(rental));
    }

    @GetMapping("/devolution/{id}")
    @Operation(summary = "Entrega dos livros", description = "Retorna uma mensagem de que o livro foi devolvido")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operação realizada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Aluguel não encontrado")
    })
    public ResponseEntity<Map<String, String>> rentRefund(@PathVariable UUID id) {
        return ResponseEntity.ok().body(rentalService.rentRefund(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza dados de um aluguel", description = "Atualiza dados de um aluguel")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Aluguel alterado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Campo inválido para alterar aluguel"),
            @ApiResponse(responseCode = "404", description = "Aluguel não encontrado"),
            @ApiResponse(responseCode = "422", description = "Campo de aluguel já cadastrado")
    })
    public ResponseEntity<RentalDTORead> update(@PathVariable UUID id, @RequestBody RentalDTO rentalToUpdated) {
        var dtoToRental = rentalService.getRentalForDto(rentalToUpdated);
        var rental = rentalService.update(id, dtoToRental);
        return ResponseEntity.ok(new RentalDTORead(rental));
    }

}
