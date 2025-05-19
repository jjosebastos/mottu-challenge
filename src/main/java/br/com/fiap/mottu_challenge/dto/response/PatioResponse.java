package br.com.fiap.mottu_challenge.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PatioResponse {
    private UUID idPatio;
    private String nome;
    private String descricao;
    private Boolean flagAberto;
    private UUID idFilial;

}
