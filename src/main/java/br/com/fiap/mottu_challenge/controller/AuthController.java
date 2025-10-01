package br.com.fiap.mottu_challenge.controller;

// ----- NOVAS IMPORTAÇÕES -----
import br.com.fiap.mottu_challenge.dto.request.FirebaseLoginRequest;
import br.com.fiap.mottu_challenge.repository.UserRepository;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
// -----------------------------

import br.com.fiap.mottu_challenge.model.auth.Credentials;
import br.com.fiap.mottu_challenge.model.auth.Token;
import br.com.fiap.mottu_challenge.model.auth.User;
import br.com.fiap.mottu_challenge.model.enums.UserRole;
import br.com.fiap.mottu_challenge.service.AuthService;
import br.com.fiap.mottu_challenge.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TokenService tokenService;

    // ----- NOVA INJEÇÃO DE DEPENDÊNCIA -----
    @Autowired
    private UserRepository userRepository; // Para criar o usuário se ele não existir
    // ----------------------------------------

    @PostMapping("/login")
    public Token login(@RequestBody Credentials credentials) {
        User user = (User) authService.loadUserByUsername(credentials.email());
        if (!passwordEncoder.matches(credentials.password(), user.getPassword())) {
            throw new BadCredentialsException("Senha incorreta");
        }
        return tokenService.createToken(user);
    }

    // ========================================================================
    // ===== NOVO MÉTODO PARA LOGIN COM FIREBASE ==============================
    // ========================================================================
    @PostMapping("/firebase-login")
    public Token firebaseLogin(@RequestBody FirebaseLoginRequest request) {
        User user;
        try {
            // 1. Valida o token do Firebase
            FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(request.firebaseToken());
            String email = decodedToken.getEmail();

            // 2. Tenta encontrar o usuário no seu banco de dados
            try {
                user = (User) authService.loadUserByUsername(email);
            } catch (UsernameNotFoundException e) {
                // 3. Se não encontrar, cria um novo usuário

                // =================== CORREÇÃO APLICADA AQUI ===================
                // A linha "newUser.setName(...)" foi removida.
                // Usamos o Builder para criar o novo usuário de forma mais elegante.
                user = userRepository.save(User.builder()
                        .email(email)
                        .password(passwordEncoder.encode(UUID.randomUUID().toString()))
                        .role(UserRole.USER) // Defina uma role padrão, ex: UserRole.USER
                        .build());
                // ==============================================================
            }

            // 4. Cria e retorna o token do seu backend
            return tokenService.createToken(user);

        } catch (Exception e) {
            throw new BadCredentialsException("Token do Firebase inválido ou expirado");
        }
    }
}