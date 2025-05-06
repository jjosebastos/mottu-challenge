package br.com.fiap.mottu_challenge.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
public class FilialRequest {
    @JsonProperty("idFilial")
    private UUID idFilial;
    @JsonProperty("cnpj")
    private String cnpj;
    @JsonProperty("nome")
    private String nome;
    @JsonProperty("cdPais")
    private String cdPais;
    @JsonProperty("dataAbertura")
    private LocalDate dataAbertura;
}
