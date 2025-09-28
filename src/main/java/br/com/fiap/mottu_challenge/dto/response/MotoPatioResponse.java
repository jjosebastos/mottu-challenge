package br.com.fiap.mottu_challenge.dto.response;

import br.com.fiap.mottu_challenge.model.enums.Setor;
import br.com.fiap.mottu_challenge.model.enums.StatusMoto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MotoPatioResponse {
    private UUID idMoto;
    private String placa;
    private StatusMoto status;
    private Setor setor;
    private String cor;
}
