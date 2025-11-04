package br.com.fiap.mottu_challenge.service;

import br.com.fiap.mottu_challenge.dto.request.PushTokenRequest;
import br.com.fiap.mottu_challenge.model.auth.User;
import br.com.fiap.mottu_challenge.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PushNotificationService {

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public void savePushToken(PushTokenRequest request) {
        // 1. Pega o email (username) do usuário autenticado no Spring Security
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();

        // 2. Busca o usuário no banco
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado: " + userEmail));

        // 3. Atualiza o token
        user.setPushToken(request.getToken());

        // 4. Salva o usuário atualizado
        userRepository.save(user);
    }
}