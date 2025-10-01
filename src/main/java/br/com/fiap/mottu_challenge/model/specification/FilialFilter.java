package br.com.fiap.mottu_challenge.model.specification;

import br.com.fiap.mottu_challenge.model.enums.CodPais;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

// Esta classe (ou record) serve para agrupar os parâmetros de filtro que vêm da URL.
public record FilialFilter(
    CodPais codPais,
    
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    LocalDate dataAberturaInicial,
    
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    LocalDate dataAberturaFinal
) {}
