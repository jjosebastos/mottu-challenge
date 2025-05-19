package br.com.fiap.mottu_challenge.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ManutencaoResponse {

    private UUID idManutencao;
    private String tipo;
    private String descricao;
    private String status;
    private UUID idMoto;
    private UUID idSensor;
}
