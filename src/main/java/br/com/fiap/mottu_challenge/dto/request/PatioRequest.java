package br.com.fiap.mottu_challenge.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class PatioRequest {
    @NotBlank(message = "Nome requerido.")
    private String nome;
    @NotBlank(message = "Descrição requerida.")
    private String descricao;
    @NotNull(message = "Flag aberto não pode ser nulo.")
    private Boolean flagAberto;
    @NotNull(message = "O id não pode ser nulo.")
    private UUID idFilial;
}
