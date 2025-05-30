package br.com.fiap.mottu_challenge.dto.request;

import br.com.fiap.mottu_challenge.model.enums.Modelo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class MotoRequest {
    @NotBlank
    private String placa;
    @NotBlank
    private Modelo modelo;
    @NotBlank
    private String chassi;
    private UUID idOperador;
    @NotNull
    private UUID idPatio;
}
