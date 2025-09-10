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
@Data
@Table(name = "t_mtu_patio")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Patio {
    @Id
    @GeneratedValue
    @UuidGenerator
    @Column(name = "idPatio",
            updatable = false,
            nullable = false,
            length = 36)
    private UUID idPatio;
    private String nome;
    private String descricao;
    private Boolean flagAberto;
    private LocalDateTime timestampCreated;
    private LocalDateTime timestampUpdated;
    @ManyToOne
    @JoinColumn(name = "filial")
    private Filial filial;

}
