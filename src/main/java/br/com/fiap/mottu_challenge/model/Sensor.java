package br.com.fiap.mottu_challenge.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Sensor {

    @Id
    @GeneratedValue
    @UuidGenerator
    @Column(name = "id_sensor",
            nullable = false,
            updatable = false,
            length = 36)
    private UUID idSensor;
    private String tipo;
    private String modelo;
    private String fabricante;
    private StatusSensor statusSensor;
    private String versaoFirmware;
    private LocalDateTime dataInstalacao;
    private LocalDate dataCalibracao;
    private LocalDateTime lastSeen;
    private Double signalStrength;
    private Boolean flagAtivo;


    @ManyToOne
    @JoinColumn(name="id_moto")
    private Moto moto;
}
