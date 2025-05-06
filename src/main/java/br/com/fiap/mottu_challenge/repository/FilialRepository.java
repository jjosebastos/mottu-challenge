package br.com.fiap.mottu_challenge.repository;

import br.com.fiap.mottu_challenge.model.Filial;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface FilialRepository extends JpaRepository<Filial, UUID> {
}
