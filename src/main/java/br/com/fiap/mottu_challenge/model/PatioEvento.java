package br.com.fiap.mottu_challenge.model;

import br.com.fiap.mottu_challenge.model.enums.TipoEvento;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "t_mtu_patio_evento")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PatioEvento {
    @Id
    @GeneratedValue
    @UuidGenerator
    @Column(name = "id_patio_evento" ,
            nullable = false,
            updatable = false,
            length = 36
    )
    private UUID idPatioEvento;
    private Double latitude;
    private Double longitude;
    private String zona;
    private TipoEvento tipoEvento;
    private LocalDateTime timestampEvento;

    @ManyToOne
    @JoinColumn(name = "id_patio")
    private Patio patio;

    @ManyToOne
    @JoinColumn(name = "id_sensor")
    private Sensor sensor;


}
