package br.com.fiap.mottu_challenge.model;

import br.com.fiap.mottu_challenge.model.converter.CodPaisConverter;
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
    @Column(name = "id_filial",
            updatable = false,
            nullable = false,
            length = 36)
    private UUID id;
    
    @Column(name = "nr_cnpj")
    private String cnpj;
    
    @Column(name = "nm_filial")
    private String nome;
    
    @Convert(converter = CodPaisConverter.class)
    @Column(name = "cd_pais")
    private CodPais codPais;
    
    @Column(name = "ts_abertura")
    private LocalDate dataAbertura;
}
