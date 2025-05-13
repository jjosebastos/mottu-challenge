package br.com.fiap.mottu_challenge.dto.response;

import br.com.fiap.mottu_challenge.model.enums.TipoTransicao;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GeofenceResponse {
    private UUID idGeofence;
    private Double latitude;
    private Double longitude;
    private Double radius;
    private String zona;
    private TipoTransicao tipoTransicao;
    private UUID idFilial;
}
