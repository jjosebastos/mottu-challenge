package br.com.fiap.mottu_challenge.dto.response;

import br.com.fiap.mottu_challenge.model.StatusSensor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SensorResponse {
    private UUID idSensor;
    private String tipo;
    private String modelo;
    private String fabricante;
    private StatusSensor statusSensor;
    private String versaoFirmware;
    private LocalDateTime dataInstalacao;
    private LocalDate dataCalibracao;
    private LocalDateTime lastSeen;
    private Double signalStrength;
    private UUID idMoto;
}
