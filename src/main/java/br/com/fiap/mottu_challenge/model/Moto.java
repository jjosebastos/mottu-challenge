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
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Moto {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String placa;
    private Modelo modelo;
    private String chassi;
    @ManyToOne
    @JsonBackReference("fi_mo")
    @JoinColumn(name = "filial_id")
    private Filial filial;
    @ManyToOne
    @JsonBackReference("op_mo")
    @JoinColumn(name = "operador_id")
    private Operador operador;

}
