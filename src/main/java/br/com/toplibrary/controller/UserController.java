package br.com.toplibrary.controller;

import br.com.toplibrary.domain.model.user.*;
import br.com.toplibrary.service.UserService;
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
import java.util.UUID;

@RestController
@RequestMapping("/users")
@Tag(name = "User Controller",  description = "REST-Controller para controle dos usuários.")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    @Operation(summary = "Busca todos os usuários", description = "Retorna uma lista com todos os usuários registrados.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operação realizada com sucesso")
    })
    public ResponseEntity<List<UserDTORead>> getUsers() {
        var listUsers = userService.findAll();
        var listUsersDto = listUsers.stream().map(UserDTORead::new).toList();
        return ResponseEntity.ok(listUsersDto);
    }

    @GetMapping("/{username}")
    @Operation(summary = "Busca usuário por id", description = "Retorna um usuário específico pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operação realizada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    public ResponseEntity<UserDTORead> getUser(@PathVariable String username) {
        var user = userService.findByUsername(username);
        return ResponseEntity.ok(new UserDTORead(user));
    }

    @PostMapping
    @Operation(summary = "Cria um usuário", description = "Cadastra um usuário no banco")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuário cadastrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Campo inválido para criar usuário"),
            @ApiResponse(responseCode = "422", description = "Campo de usuário já cadastrado")
    })
    public ResponseEntity<UserDTORead> create(@RequestBody @Valid UserDTO user) {
        var newUser = new User(user.email(), user.username(), user.name(), user.password());
        userService.save(newUser);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newUser.getId())
                .toUri();

        return ResponseEntity.created(location).body(new UserDTORead(newUser));
    }

    @PostMapping("/admin")
    @Operation(summary = "Cria um Admin", description = "Cadastra um Admin no banco")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Admin criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Campo inválido para criar usuário"),
            @ApiResponse(responseCode = "422", description = "Campo de usuário já cadastrado")
    })
    public ResponseEntity<UserDTORead> createAdmin(@RequestBody @Valid UserDTO user) {
        var newUser = new User(user.email(), user.username(), user.name(), user.password(), UserRole.ADMIN);
        userService.save(newUser);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newUser.getId())
                .toUri();

        return ResponseEntity.created(location).body(new UserDTORead(newUser));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza dados de um usuário", description = "Atualiza dados de um usuário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário alterado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado"),
            @ApiResponse(responseCode = "422", description = "Campo de usuário já cadastrado")
    })
    public ResponseEntity<UserDTORead> update(@PathVariable UUID id, @RequestBody UserDTOUpdate userDTO) {
        var update = new User(userDTO.email(), userDTO.username(), userDTO.name(), userDTO.password());
        var userUpdated = userService.update(id, update);
        return ResponseEntity.ok(new UserDTORead(userUpdated));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deleta um usuário", description = "Deleta um usuário pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Usuário deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
