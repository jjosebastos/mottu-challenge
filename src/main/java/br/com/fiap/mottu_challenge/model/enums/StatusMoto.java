package br.com.fiap.mottu_challenge.model.enums;

import lombok.Getter;

@Getter
public enum StatusMoto {
    LIVRE("livre", "Verde"),
    MANUTENCAO("manutenção", "Amarelo"),
    PROBLEMA("problema", "Vermelho");

    private final String status;
    private final String cor;

    StatusMoto(String status, String cor) {
        this.status = status;
        this.cor = cor;
    }
}
