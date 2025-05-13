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
    @NotBlank(message = "Placa não pode estar em branco.")
    private String placa;
    @NotBlank(message = "Modelo não pode estar em branco.")
    private Modelo modelo;
    @NotBlank(message = "Chassi não pode estar em branco.")
    private String chassi;
    @NotNull(message = "Id Operador não pode ser nulo.")
    private UUID idOperador;
    @NotNull(message = "Id Filial não pode ser nulo.")
    private UUID idFilial;
}
