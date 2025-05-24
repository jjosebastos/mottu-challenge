package br.com.fiap.mottu_challenge.repository;

import br.com.fiap.mottu_challenge.model.Endereco;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface EnderecoRepository extends JpaRepository<Endereco, UUID>, JpaSpecificationExecutor<Endereco> {

    Page<Endereco> findByCidadeIgnoreCase(String cidade, Pageable pageable);
}
