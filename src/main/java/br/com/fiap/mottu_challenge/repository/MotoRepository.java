package br.com.fiap.mottu_challenge.repository;

import br.com.fiap.mottu_challenge.model.Moto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MotoRepository extends JpaRepository<Moto, UUID> {

    Moto findByIdMotoAndFlagAtivoTrue(UUID idMoto);
}
