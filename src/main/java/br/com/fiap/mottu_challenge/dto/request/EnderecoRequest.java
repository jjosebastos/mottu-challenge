package br.com.fiap.mottu_challenge.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class EnderecoRequest {
    private UUID idFilial;
    @NotBlank(message = "Logradouro não pode ser branco.")
    private String logradouro;
    @NotBlank(message = "Numero não pode ser branco.")
    private String numero;
    @NotBlank(message = "Bairro não pode ser branco.")
    private String bairro;
    @NotBlank(message = "Cidade não pode ser branco.")
    private String cidade;
    @NotBlank(message = "Uf não pode ser branco.")
    private String uf;
    @NotBlank(message = "Cep não pode ser branco.")
    private String cep;
    @NotBlank(message = "Complemento não pode ser branco.")
    private String complemento;
}
