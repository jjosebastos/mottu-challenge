package br.com.fiap.mottu_challenge.dto.request;


import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;


@Getter
@Setter
public class ManutencaoRequest {
    @NotBlank
    private String tipo;
    @NotBlank
    private String descricao;
    @NotBlank
    private String status;
    private UUID idMoto;
    private UUID idSensor;
}
