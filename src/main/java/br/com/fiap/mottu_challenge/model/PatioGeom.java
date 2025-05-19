package br.com.fiap.mottu_challenge.model;

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
public class PatioGeom {

    @Id
    @GeneratedValue
    @UuidGenerator
    @Column(name = "idPatioGeom",nullable = false, updatable = false, length = 36)
    private UUID idPatioGeom;
    private Double latitudeMin;
    private Double longitudeMin;
    private Double latitudeMax;
    private Double longitudeMax;
    private Boolean flagAtivo;
    @ManyToOne
    @JoinColumn(name = "patio")
    private Patio patio;
}
