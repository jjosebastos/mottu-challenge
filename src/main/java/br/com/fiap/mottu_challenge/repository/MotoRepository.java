package br.com.fiap.mottu_challenge.repository;

import br.com.fiap.mottu_challenge.model.Moto;
import br.com.fiap.mottu_challenge.model.enums.Setor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MotoRepository extends JpaRepository<Moto, UUID> {

    Moto findByIdMotoAndFlagAtivoTrue(UUID idMoto);
    
    List<Moto> findBySetorAndFlagAtivoTrue(Setor setor);
    
    Optional<Moto> findByPlacaAndFlagAtivoTrue(String placa);
}
