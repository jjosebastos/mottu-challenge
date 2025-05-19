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
public class PatioGeomResponse {
    private UUID idPatioGeom;
    private Double longitudeMin;
    private Double latitudeMin;
    private Double longitudeMax;
    private Double latitudeMax;
    private UUID idPatio;
}
