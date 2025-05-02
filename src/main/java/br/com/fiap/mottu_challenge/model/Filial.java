package br.com.fiap.mottu_challenge.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Filial {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;
    @Size (max = 17)
    private String cnpj;
    private String codPais;
    private LocalDate dataAbertura;
}
