package br.com.fiap.mottu_challenge.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
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
    @Column(name = "idSensor",
            nullable = false,
            updatable = false,
            length = 36)
    private UUID idSensor;
    private String tipo;
    private String firmware;
    private LocalDateTime dataHoraAtualizacao;
}
