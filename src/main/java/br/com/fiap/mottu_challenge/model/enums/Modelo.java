package br.com.fiap.mottu_challenge.model.enums;

import lombok.Getter;

@Getter
public enum Modelo {
    MOTTUPOP("1"),
    MOTTUSPORT("2"),
    MOTTUE("3");

    private final String modelo;

    Modelo(String modelo) {
        this.modelo = modelo;
    }
}
