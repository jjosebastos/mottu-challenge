package br.com.fiap.mottu_challenge.repository;

import br.com.fiap.mottu_challenge.model.Patio;
import br.com.fiap.mottu_challenge.model.PatioGeom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PatioGeomRepository extends JpaRepository<PatioGeom, UUID> {
    PatioGeom findByIdPatioGeomAndFlagAtivoTrue(UUID idPatioGeom);
}
