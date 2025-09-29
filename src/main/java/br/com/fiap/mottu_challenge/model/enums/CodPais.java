package br.com.fiap.mottu_challenge.model.enums;

import lombok.Getter;

@Getter
public enum CodPais {
    BR("BR"),  // Código ISO 3166-1
    MX("MX"),
    US("US"),
    AR("AR");

    private final String codigo;

    CodPais(String codigo) {
        this.codigo = codigo;
    }

    // Método para encontrar por código
    public static CodPais fromCodigo(String codigo) {
        for (CodPais pais : values()) {
            if (pais.codigo.equals(codigo)) {
                return pais;
            }
        }
        throw new IllegalArgumentException("Código de país inválido: " + codigo);
    }
}