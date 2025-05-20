package br.com.fiap.mottu_challenge.model.enums;

import lombok.Getter;

@Getter
public enum TipoEvento {
    ENTRADA("1"),
    SAIDA("2");
    private final String value;
    TipoEvento(String value) {
        this.value = value;
    }

}
