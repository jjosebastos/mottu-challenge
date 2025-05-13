package br.com.fiap.mottu_challenge.controller;

import br.com.fiap.mottu_challenge.model.auth.Credentials;
import br.com.fiap.mottu_challenge.model.auth.Token;
import br.com.fiap.mottu_challenge.model.auth.User;
import br.com.fiap.mottu_challenge.service.AuthService;
import br.com.fiap.mottu_challenge.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public Token login(@RequestBody Credentials credentials){
        User user = (User) authService.loadUserByUsername(credentials.email());
        if (!passwordEncoder.matches(credentials.password(), user.getPassword())){
            throw new BadCredentialsException("Senha incorreta");
        }
        return tokenService.createToken(user);
    }

}
