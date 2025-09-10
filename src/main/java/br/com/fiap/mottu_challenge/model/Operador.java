package br.com.fiap.mottu_challenge.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Data
@Table(name = "t_mtu_operador")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Operador {
    @Id
    @GeneratedValue
    @UuidGenerator
    @Column(name = "idFilial",
            updatable = false,
            nullable = false,
            length = 36)
    private UUID idOperador;
    private String nome;
    private String cpf;
    private String rg;
    private LocalDate dataNascimento;
}
