package br.com.fiap.mottu_challenge.repository;

import br.com.fiap.mottu_challenge.model.Manutencao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ManutencaoRepository extends JpaRepository<Manutencao, UUID> {
}
