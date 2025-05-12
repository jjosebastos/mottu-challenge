package br.com.fiap.mottu_challenge.model.enums;

import lombok.Getter;

@Getter
public enum StatusLocalizacao {
    PATIO("1"),
    RUA("2"),
    MANUTENCAO("3");
    private final String value;
    StatusLocalizacao(String value) {
        this.value = value;
    }

}
