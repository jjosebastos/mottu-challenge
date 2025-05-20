package br.com.fiap.mottu_challenge.model;

import lombok.Getter;

@Getter
public enum StatusSensor {
    ATIVO("1"),
    INATIVO("2"),
    MANUTENCAO("3"),
    DESATIVADO("4");

    private final String status;

    StatusSensor(String status) {
        this.status = status;
    }
}
