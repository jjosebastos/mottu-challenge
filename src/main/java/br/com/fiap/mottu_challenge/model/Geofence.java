package br.com.fiap.mottu_challenge.model;

import br.com.fiap.mottu_challenge.model.enums.TipoTransicao;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Geofence {
    @Id
    @GeneratedValue
    @UuidGenerator
    @Column(name = "idGeoFence" ,
            nullable = false,
            updatable = false,
            length = 36
    )
    private UUID idGeofence;
    private Double latitude;
    private Double longitude;
    private Double radius;
    private String zona;
    private TipoTransicao tipoTransicao;
    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "filial_id")
    private Filial filial;

}
