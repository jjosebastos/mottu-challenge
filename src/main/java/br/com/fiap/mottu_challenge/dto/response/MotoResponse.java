package br.com.fiap.mottu_challenge.dto.response;

import br.com.fiap.mottu_challenge.model.enums.Modelo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MotoResponse {
    private UUID idMoto;
    private String placa;
    private Modelo modelo;
    private String chassi;
    private UUID idFilial;
    private UUID idOperador;
}
