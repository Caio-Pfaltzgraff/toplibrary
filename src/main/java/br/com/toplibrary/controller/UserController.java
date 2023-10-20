package br.com.toplibrary.controller;

import br.com.toplibrary.domain.model.user.*;
import br.com.toplibrary.service.UserService;
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
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<UserDTORead>> getUsers() {
        var listUsers = userService.findAll();
        var listUsersDto = listUsers.stream().map(UserDTORead::new).toList();
        return ResponseEntity.ok(listUsersDto);
    }

    @GetMapping("/{username}")
    public ResponseEntity<UserDTORead> getUser(@PathVariable String username) {
        var user = userService.findByUsername(username);
        return ResponseEntity.ok(new UserDTORead(user));
    }

    @PostMapping
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
    public ResponseEntity<UserDTORead> update(@PathVariable UUID id, @RequestBody UserDTOUpdate userDTO) {
        var update = new User(userDTO.email(), userDTO.username(), userDTO.name(), userDTO.password());
        var userUpdated = userService.update(id, update);
        return ResponseEntity.ok(new UserDTORead(userUpdated));
    }

}
