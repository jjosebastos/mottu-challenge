package br.com.fiap.mottu_challenge.repository;

import br.com.fiap.mottu_challenge.model.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface SensorRepository extends JpaRepository<Sensor, UUID> {
    @Query("""
      SELECT s
      FROM Sensor s
      WHERE s.idSensor = :idSensor
        AND s.flagAtivo = true
        AND s.statusSensor <> br.com.fiap.mottu_challenge.model.StatusSensor.DESATIVADO
    """)
    Optional<Sensor> findActiveById(@Param("idSensor") UUID idSensor);
}
