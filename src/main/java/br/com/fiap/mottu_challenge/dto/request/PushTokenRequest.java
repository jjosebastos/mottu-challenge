package br.com.fiap.mottu_challenge.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PushTokenRequest {
    
    @NotBlank(message = "O token não pode estar vazio")
    private String token;
}