package br.com.fiap.mottu_challenge.service;

import br.com.fiap.mottu_challenge.model.auth.Token;
import br.com.fiap.mottu_challenge.model.auth.User;
import br.com.fiap.mottu_challenge.model.enums.UserRole;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Service
public class TokenService {
    private Instant expiresAt = LocalDateTime.now().plusMinutes(120).atZone(ZoneId.systemDefault()).toInstant();
    private Algorithm algorithm = Algorithm.HMAC256("secret");

    public Token createToken(User user){
        var jwt = JWT.create()
                .withSubject(user.getId().toString())
                .withClaim("email", user.getEmail())
                .withClaim("role", user.getRole().toString())
                .withExpiresAt(expiresAt)
                .sign(algorithm);
        return new Token(jwt, user.getEmail());
    }

    public User getUserFromToken(String jwt){
        var jwtVerified = JWT.require(algorithm).build().verify(jwt);
        return User.builder()
                .id(Long.valueOf(jwtVerified.getSubject()))
                .email(jwtVerified.getClaim("email").asString())
                .role(UserRole.ADMIN)
                .build();
    }
}
