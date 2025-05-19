package br.com.fiap.mottu_challenge.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class PatioGeomRequest {
    @NotNull
    private Double latitudeMinima;
    @NotNull
    private Double longitudeMinima;
    @NotNull
    private Double latitudeMaxima;
    @NotNull
    private Double longitudeMaxima;
    @NotNull
    private UUID idPatio;
}
