package br.com.fiap.mottu_challenge.dto.request;

import br.com.fiap.mottu_challenge.model.enums.StatusMoto;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AtualizarStatusMotoRequest {
    @NotNull
    private StatusMoto status;
}
