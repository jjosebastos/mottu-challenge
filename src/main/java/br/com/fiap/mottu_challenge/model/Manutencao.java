package br.com.fiap.mottu_challenge.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "t_mtu_manutencao")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Manutencao {

    @Id
    @GeneratedValue
    @UuidGenerator
    @Column (name = "idManutencao",
            updatable = false,
            nullable = false,
            length = 36
    )
    private UUID idManutencao;
    private String tipo;
    private String descricao;
    private Boolean flagAtivo;
    private LocalDateTime timestampCreated;
    private LocalDateTime timestampUpdated;
    private String status;

    @ManyToOne(optional = true)
    @JoinColumn(name = "moto")
    private Moto moto;

    @ManyToOne(optional = true)
    @JoinColumn(name = "sensor")
    private Sensor sensor;


}
