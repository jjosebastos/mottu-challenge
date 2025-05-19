package br.com.fiap.mottu_challenge.repository;

import br.com.fiap.mottu_challenge.model.Patio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PatioRepository extends JpaRepository<Patio, UUID> {
    Patio findByIdPatioAndFlagAbertoTrue(UUID idPatio);
}
