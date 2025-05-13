package br.com.fiap.mottu_challenge.model.enums;

import lombok.Getter;

@Getter
public enum TipoTransicao {
    ENTRADA("1"),
    SAIDA("2");
    private final String value;
    TipoTransicao(String value) {
        this.value = value;
    }

}
