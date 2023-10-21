package br.com.toplibrary.controller;

import br.com.toplibrary.domain.model.login.Login;
import br.com.toplibrary.domain.model.login.TokenDTO;
import br.com.toplibrary.domain.model.user.User;
import br.com.toplibrary.infra.security.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity<TokenDTO> login(@RequestBody Login login) {
        var authToken = new UsernamePasswordAuthenticationToken(login.username(), login.password());
        var auth = authenticationManager.authenticate(authToken);

        var token = tokenService.generateToken((User) auth.getPrincipal());

        return ResponseEntity.ok(new TokenDTO(token));
    }

}
