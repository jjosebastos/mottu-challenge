package br.com.fiap.mottu_challenge.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor 
@AllArgsConstructor
public class ExpoPushMessage {

    private List<String> to;
    
    // O título da notificação
    private String title;
    
    private String body;
}