package br.com.fiap.mottu_challenge.repository;

import br.com.fiap.mottu_challenge.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String username);
}
