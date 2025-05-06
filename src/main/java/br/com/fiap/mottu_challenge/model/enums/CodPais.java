package br.com.fiap.mottu_challenge.model.enums;

import lombok.Getter;

@Getter
public enum CodPais {
    BRA("1"),
    MEX("2");

    private String codPais;
    CodPais(String codPais) {
        this.codPais = codPais;
    }
}
