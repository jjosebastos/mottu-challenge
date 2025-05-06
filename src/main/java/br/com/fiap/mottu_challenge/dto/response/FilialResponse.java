package br.com.fiap.mottu_challenge.dto.response;


import br.com.fiap.mottu_challenge.model.enums.CodPais;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FilialResponse {
    private UUID idFilial;
    private String cnpj;
    private String nome;
    private CodPais cdPais;
    private LocalDate dataAbertura;
}
