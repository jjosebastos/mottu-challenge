package br.com.fiap.mottu_challenge.service;

import br.com.fiap.mottu_challenge.dto.request.ExpoPushMessage;
import br.com.fiap.mottu_challenge.repository.UserRepository;

// Imports para logging
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// Imports para a chamada HTTP (RestTemplate)
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

// Import para rodar em segundo plano
import org.springframework.scheduling.annotation.Async;

import java.util.List;

/**
 * Serviço responsável por enviar notificações Push
 * para a API do Expo.
 */
@Service
public class ExpoNotificationService {

    // URL oficial da API do Expo
    private static final String EXPO_PUSH_URL = "https://api.expo.dev/v2/push/send";
    
    // Logger para vermos o que está acontecendo
    private static final Logger logger = LoggerFactory.getLogger(ExpoNotificationService.class);

    @Autowired
    private UserRepository userRepository;

    /**
     * Envia uma notificação para TODOS os dispositivos registrados no banco.
     * * @param title O título da notificação (ex: "Nova Filial")
     * @param body A mensagem da notificação (ex: "Filial X foi cadastrada.")
     */
    @Async // <-- Diz ao Spring para rodar isso em "segundo plano" (graças ao @EnableAsync do Passo 1)
    public void sendNotificationToAll(String title, String body) {
        
        // 1. Busca todos os tokens (que não são nulos) no banco
        // (Usando o método que criamos no Passo 2)
        List<String> pushTokens = userRepository.findAllPushTokens();

        if (pushTokens.isEmpty()) {
            logger.info("Nenhum token de notificação encontrado. Nenhuma notificação será enviada.");
            return; // Sai da função se não houver ninguém para notificar
        }

        logger.info("Enviando notificação para " + pushTokens.size() + " dispositivo(s)...");

        // 2. Prepara a chamada HTTP
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        // O Expo não requer um token de autenticação para este endpoint

        // 3. Cria o corpo da mensagem
        // (Usando o DTO que criamos no Passo 3)
        ExpoPushMessage payload = new ExpoPushMessage(pushTokens, title, body);

        // 4. Monta a requisição (Cabeçalho + Corpo)
        HttpEntity<ExpoPushMessage> request = new HttpEntity<>(payload, headers);

        // 5. Envia a notificação (POST) para a API do Expo
        try {
            String response = restTemplate.postForObject(EXPO_PUSH_URL, request, String.class);
            logger.info("Resposta da API do Expo: " + response);
        } catch (Exception e) {
            // Se a API do Expo estiver fora ou der erro, apenas logamos
            logger.error("Erro ao enviar notificação para o Expo: " + e.getMessage());
        }
    }
}