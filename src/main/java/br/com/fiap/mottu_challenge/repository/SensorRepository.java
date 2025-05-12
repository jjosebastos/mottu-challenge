package br.com.fiap.mottu_challenge.repository;

import br.com.fiap.mottu_challenge.model.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SensorRepository extends JpaRepository<Sensor, UUID> {
}
