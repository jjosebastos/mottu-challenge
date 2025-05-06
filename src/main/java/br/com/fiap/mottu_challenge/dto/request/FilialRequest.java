package br.com.fiap.mottu_challenge.dto.request;

import br.com.fiap.mottu_challenge.model.enums.CodPais;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
public class FilialRequest {
    private UUID idFilial;
    @NotBlank(message = "O valor do CNPJ não pode ser vazio")
    @Size(max = 17, message = "O CNPJ deve ter no máximo 17 caracteres")
    private String cnpj;
    @NotBlank(message = "O valor do nome não pode ser vazio")
    private String nome;
    private CodPais cdPais;
    private LocalDate dataAbertura;
}
