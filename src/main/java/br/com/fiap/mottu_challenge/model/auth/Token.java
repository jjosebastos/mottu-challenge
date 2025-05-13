package br.com.fiap.mottu_challenge.model.auth;

public record Token(
        String token,
        String email
) {}
