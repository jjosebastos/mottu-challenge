package br.com.fiap.mottu_challenge.repository;

import br.com.fiap.mottu_challenge.model.Geofence;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface GeofenceRepository extends JpaRepository<Geofence, UUID> {
}
