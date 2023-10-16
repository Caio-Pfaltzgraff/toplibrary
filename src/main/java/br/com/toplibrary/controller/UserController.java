package br.com.toplibrary.controller;

import br.com.toplibrary.domain.model.user.User;
import br.com.toplibrary.domain.model.user.UserRole;
import br.com.toplibrary.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity getUsers() {
        var ListUsers = userService.findAll();
        return ResponseEntity.ok(ListUsers);
    }

    @GetMapping("/{username}")
    public ResponseEntity getUser(@PathVariable String username) {
        var user = userService.findByUsername(username);
        return ResponseEntity.ok(user);
    }

    @PostMapping
    public ResponseEntity create(@RequestBody User user) {
        var newUser = new User(user.getEmail(), user.getUsername(), user.getName(), user.getPassword());
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(newUser));
    }

    @PostMapping("/admin")
    public ResponseEntity createAdmin(@RequestBody User user) {
        var newUser = new User(user.getEmail(), user.getUsername(), user.getName(), user.getPassword(), UserRole.ADMIN);
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(newUser));
    }
}
