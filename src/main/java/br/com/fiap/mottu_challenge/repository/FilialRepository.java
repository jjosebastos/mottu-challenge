package br.com.fiap.mottu_challenge.repository;

import br.com.fiap.mottu_challenge.dto.response.FilialResponse;
import br.com.fiap.mottu_challenge.model.Filial;
import br.com.fiap.mottu_challenge.model.enums.CodPais;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface FilialRepository extends JpaRepository<Filial, UUID>, JpaSpecificationExecutor<Filial> {

    Page<Filial> findByCodPais(CodPais codPais, Pageable pageable);
}
