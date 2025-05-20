package br.com.fiap.mottu_challenge.dto.request;

import br.com.fiap.mottu_challenge.model.enums.TipoEvento;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class PatioEventoRequest {

    @Min(value = -90, message = "Latitude mínima é -90.")
    @Max(value =  90, message = "Latitude máxima é +90.")
    private Double latitude;
    @Min(value = -180, message = "Longitude mínima é -180.")
    @Max(value =  180, message = "Longitude máxima é +180.")
    private Double longitude;
    @NotBlank
    private String zona;
    @NotNull
    private TipoEvento tipoEvento;
    @NotNull
    private UUID idMoto;
    @NotNull
    private UUID idPatio;
    @NotNull
    private UUID idSensor;

}
