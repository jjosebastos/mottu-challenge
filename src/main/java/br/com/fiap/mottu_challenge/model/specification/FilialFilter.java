package br.com.fiap.mottu_challenge.model.specification;

import br.com.fiap.mottu_challenge.model.enums.CodPais;

import java.time.LocalDate;

public record FilialFilter (
        LocalDate dataAberturaInicial,
        LocalDate dataAberturaFinal,
        CodPais codPais) {
}
