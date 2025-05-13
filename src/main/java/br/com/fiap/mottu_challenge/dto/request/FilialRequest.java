package br.com.fiap.mottu_challenge.dto.request;

import br.com.fiap.mottu_challenge.model.enums.CodPais;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
public class FilialRequest {
    @NotBlank(message = "Cnpj não pode ser vazio.")
    @Size(max = 17, message = "Cnpj deve ter no máximo 17 caracteres.")
    private String cnpj;
    @NotBlank(message = "Nome não pode ser vazio.")
    private String nome;
    @NotNull(message = "Codigo do Pais não pode ser vazio.")
    private CodPais cdPais;
    @NotNull
    private LocalDate dataAbertura;
}
