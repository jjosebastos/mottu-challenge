package br.com.fiap.mottu_challenge.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SensorResponse {
    private UUID idSensor;
    private String tipoSensor;
    private String firmware;
    private LocalDateTime dataHoraAtualizacao;

}
