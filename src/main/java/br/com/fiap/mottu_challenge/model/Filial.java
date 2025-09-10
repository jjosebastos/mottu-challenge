package br.com.fiap.mottu_challenge.model;

import br.com.fiap.mottu_challenge.model.enums.CodPais;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "t_mtu_filial")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Filial {
    @Id
    @GeneratedValue
    @UuidGenerator
    @Column(name = "idFilial",
            updatable = false,
            nullable = false,
            length = 36)
    private UUID id;
    private String cnpj;
    private String nome;
    @Enumerated(EnumType.STRING)
    private CodPais codPais;
    private LocalDate dataAbertura;
}
