package br.com.fiap.mottu_challenge.repository;

import br.com.fiap.mottu_challenge.model.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EnderecoRepository extends JpaRepository<Endereco, UUID> {
}
