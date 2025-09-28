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
    @Column(name = "id_patio",
            updatable = false,
            nullable = false,
            length = 36)
    private UUID idPatio;
    
    @Column(name = "nm_patio")
    private String nome;
    
    @Column(name = "ds_patio")
    private String descricao;
    
    @Column(name = "fl_aberto", columnDefinition = "char(1)")
    private String flagAberto;
    
    @Column(name = "ts_created", columnDefinition = "timestamptz")
    private LocalDateTime timestampCreated;
    
    @Column(name = "ts_update", columnDefinition = "timestamptz")
    private LocalDateTime timestampUpdated;
    
    @ManyToOne
    @JoinColumn(name = "id_filial")
    private Filial filial;

}
