package br.com.fiap.mottu_challenge.controller;

import br.com.fiap.mottu_challenge.dto.request.PushTokenRequest;
import br.com.fiap.mottu_challenge.service.PushNotificationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/push") // O prefixo /api/push
public class PushNotificationController {

    @Autowired
    private PushNotificationService pushNotificationService;

    @PostMapping("/register") // O endpoint completo: POST /api/push/register
    public ResponseEntity<Void> registerPushToken(@RequestBody @Valid PushTokenRequest request) {
        
        // Delega toda a lógica para o serviço
        pushNotificationService.savePushToken(request);
        
        // Retorna 200 OK sem corpo
        return ResponseEntity.ok().build();
    }
}