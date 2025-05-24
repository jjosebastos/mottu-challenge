package br.com.fiap.mottu_challenge.config;

import br.com.fiap.mottu_challenge.model.auth.User;
import br.com.fiap.mottu_challenge.model.enums.UserRole;
import br.com.fiap.mottu_challenge.repository.EnderecoRepository;
import br.com.fiap.mottu_challenge.repository.FilialRepository;
import br.com.fiap.mottu_challenge.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Configuration
public class DatabaseSeeder {


    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;


    @Autowired
    private FilialRepository filialRepository;

    @Autowired
    private EnderecoRepository enderecoRepository;


    @PostConstruct
    public void init() {
        userRepository.saveAll(List.of(
                User.builder()
                        .email("jose@mail.com")
                        .password(passwordEncoder.encode("12345"))
                        .role(UserRole.ADMIN)
                        .build(),

                User.builder()
                        .email("maria@fiap.com.br")
                        .password(passwordEncoder.encode("12345"))
                        .role(UserRole.USER)
                        .build()));
    }

}
