package br.com.fiap.mottu_challenge.dto.response;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OperadorResponse {
    private UUID id;
    private String nome;
    private String cpf;
    private String rg;
    private LocalDate dataNascimento;
}
