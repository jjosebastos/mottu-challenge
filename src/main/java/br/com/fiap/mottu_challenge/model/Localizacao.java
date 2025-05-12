package br.com.fiap.mottu_challenge.model;

import br.com.fiap.mottu_challenge.model.enums.StatusLocalizacao;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Localizacao {

    @Id
    @GeneratedValue
    @UuidGenerator
    @Column(name = "idFilial",
            updatable = false,
            nullable = false,
            length = 36)
    private UUID idLocalizacao;
    private LocalDate dataAtualizacao;
    private StatusLocalizacao statusLocalizacao;

    @JsonBackReference("mo_lo")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "moto_id")
    private Moto moto;
}
