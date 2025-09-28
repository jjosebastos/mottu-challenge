package br.com.fiap.mottu_challenge.dto.response;

import br.com.fiap.mottu_challenge.model.enums.Setor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SetorResponse {
    private Setor setor;
    private String nome;
    private List<MotoPatioResponse> motos;
    private int totalMotos;
    private int motosLivres;
    private int motosManutencao;
    private int motosProblema;
}
