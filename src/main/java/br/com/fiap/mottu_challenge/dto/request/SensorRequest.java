package br.com.fiap.mottu_challenge.dto.request;


import br.com.fiap.mottu_challenge.model.StatusSensor;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class SensorRequest {
    @NotBlank
    private String tipo;
    @NotBlank
    private String modelo;
    @NotBlank
    private String fabricante;
    @NotNull
    private StatusSensor statusSensor;
    @NotBlank(message = "Versão do firmware não pode ser em branco.")
    private String versaoFirmware;
    @PastOrPresent
    private LocalDateTime dataInstalacao;
    @PastOrPresent
    private LocalDate dataCalibracao;
    private LocalDateTime lastSeen;
    @Positive
    private Double signalStrength;
    @NotNull
    private UUID idMoto;

}
