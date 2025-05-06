package br.com.fiap.mottu_challenge.model;

import br.com.fiap.mottu_challenge.model.enums.CodPais;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Filial {
    @Id
    @GeneratedValue
    @UuidGenerator
    @Column(name = "idFilial",
            updatable = false,
            nullable = false,
            length = 36)
    private UUID id;

    @Size (max = 17)
    private String cnpj;
    @NotBlank(message = "O valor do nome näo ser nulo")
    private String nome;
    @Enumerated(EnumType.STRING)
    private CodPais codPais;
    private LocalDate dataAbertura;
}
