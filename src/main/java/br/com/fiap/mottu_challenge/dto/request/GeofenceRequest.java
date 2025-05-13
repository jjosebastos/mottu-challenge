package br.com.fiap.mottu_challenge.dto.request;

import br.com.fiap.mottu_challenge.model.enums.TipoTransicao;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class GeofenceRequest {

    @Min(value = -90, message = "Latitude mínima é -90")
    @Max(value =  90, message = "Latitude máxima é +90")
    private Double latitude;
    @Min(value = -180, message = "Longitude mínima é -180")
    @Max(value =  180, message = "Longitude máxima é +180")
    private Double longitude;
    @Min(value = 0, message = "Raio mínimo é 0 metros")
    private Double radius;
    @NotBlank(message = "Nome da zona...")
    private String zona;
    @NotNull(message = "Tipo de transição deve ser ENTRADA ou SAIDA")
    private TipoTransicao tipoTransicao;
    @NotNull(message = "UUID da filial não pode ser vazio")
    private UUID idFilial;

}
