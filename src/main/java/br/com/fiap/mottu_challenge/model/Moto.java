package br.com.fiap.mottu_challenge.model;

import br.com.fiap.mottu_challenge.model.enums.Modelo;
import br.com.fiap.mottu_challenge.model.enums.Setor;
import br.com.fiap.mottu_challenge.model.enums.StatusMoto;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Data
@Table(name = "t_mtu_moto")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Moto {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id_moto")
    private UUID idMoto;
    
    @Column(name = "nr_placa")
    private String placa;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "nm_modelo")
    private Modelo modelo;
    
    @Column(name = "nr_chassi")
    private String chassi;
    
    @Enumerated(EnumType.STRING)
    private StatusMoto status;
    
    @Enumerated(EnumType.STRING)
    private Setor setor;

    @Column(name = "fl_status")
    private Boolean flagAtivo;

    @JsonBackReference("pa_mo")
    @ManyToOne
    @JoinColumn(name = "id_patio")
    private Patio patio;

    @JsonBackReference("op_mo")
    @ManyToOne(optional = true)
    @JoinColumn(name = "id_operador", nullable = true)
    private Operador operador;

}
