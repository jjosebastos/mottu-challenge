package br.com.fiap.mottu_challenge.model.enums;

import lombok.Getter;

@Getter
public enum Setor {
    A("Setor A"),
    B("Setor B"),
    C("Setor C"),
    D("Setor D");

    private final String nome;

    Setor(String nome) {
        this.nome = nome;
    }
}
