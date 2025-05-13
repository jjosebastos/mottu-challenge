package br.com.fiap.mottu_challenge.dto.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jdk.jfr.Timestamp;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class SensorRequest {
    @NotBlank(message = "Tipo de sensor não pode ser em branco.")
    private String tipo;
    @NotBlank(message = "Versão do firmware não pode ser em branco.")
    private String firmware;
    private LocalDateTime dataHoraAtualizacao;

}
