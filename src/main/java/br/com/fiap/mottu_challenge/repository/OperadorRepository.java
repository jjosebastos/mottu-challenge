package br.com.fiap.mottu_challenge.repository;

import br.com.fiap.mottu_challenge.model.Operador;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OperadorRepository extends JpaRepository<Operador, UUID> {
}
