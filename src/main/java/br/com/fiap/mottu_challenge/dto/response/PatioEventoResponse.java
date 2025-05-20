package br.com.fiap.mottu_challenge.dto.response;

import br.com.fiap.mottu_challenge.model.enums.TipoEvento;
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
public class PatioEventoResponse {
    private UUID idPatioEvento;
    private Double latitude;
    private Double longitude;
    private String zona;
    private TipoEvento tipoEvento;
    private LocalDateTime timestampEvento;
    private UUID idPatio;
    private UUID idMoto;
    private UUID idSensor;
}
