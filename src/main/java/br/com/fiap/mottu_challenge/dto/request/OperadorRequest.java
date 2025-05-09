package br.com.fiap.mottu_challenge.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
public class OperadorRequest {

    private UUID id;
    @NotBlank(message = "Nome não pode ser vazio")
    private String nome;
    @NotBlank(message = "Cpf não pode ser vazio")
    private String cpf;
    @NotBlank(message = "Rg não pode ser vazio")
    private String rg;
    @Past(message = "Data de n")
    private LocalDate dataNascimento;
}
