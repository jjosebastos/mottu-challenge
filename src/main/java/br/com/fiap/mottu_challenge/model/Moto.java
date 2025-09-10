package br.com.fiap.mottu_challenge.model;

import br.com.fiap.mottu_challenge.model.enums.Modelo;
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
    private UUID idMoto;
    private String placa;
    private Modelo modelo;
    private String chassi;

    private Boolean flagAtivo;

    @JsonBackReference("pa_mo")
    @ManyToOne
    @JoinColumn(name = "patio")
    private Patio patio;

    @JsonBackReference("op_mo")
    @ManyToOne(optional = true)
    @JoinColumn(name = "operador", nullable = true)
    private Operador operador;

}
