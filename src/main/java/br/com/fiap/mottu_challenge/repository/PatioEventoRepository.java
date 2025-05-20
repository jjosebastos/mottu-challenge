package br.com.fiap.mottu_challenge.repository;

import br.com.fiap.mottu_challenge.model.PatioEvento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PatioEventoRepository extends JpaRepository<PatioEvento, UUID> {
}
