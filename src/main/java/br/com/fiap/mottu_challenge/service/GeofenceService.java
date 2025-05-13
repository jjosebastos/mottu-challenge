package br.com.fiap.mottu_challenge.service;

import br.com.fiap.mottu_challenge.dto.request.GeofenceRequest;
import br.com.fiap.mottu_challenge.dto.response.GeofenceResponse;
import br.com.fiap.mottu_challenge.model.Geofence;
import br.com.fiap.mottu_challenge.repository.FilialRepository;
import br.com.fiap.mottu_challenge.repository.GeofenceRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class GeofenceService {

    @Autowired
    private GeofenceRepository geofenceRepository;

    @Autowired
    private FilialRepository filialRepository;

    @Transactional
    public GeofenceResponse create (GeofenceRequest request){
        var filialId = request.getIdFilial();
        var found = this.filialRepository.findById(filialId)
                .orElseThrow(NoSuchElementException::new);
        var geofence = new Geofence();
        geofence.setLatitude(request.getLatitude());
        geofence.setLongitude(request.getLongitude());
        geofence.setRadius(request.getRadius());
        geofence.setZona(request.getZona());
        geofence.setTipoTransicao(request.getTipoTransicao());
        geofence.setFilial(found);
        var saved = this.geofenceRepository.save(geofence);
        return this.toGeofenceResponse(saved);

    }

    @Transactional
    public GeofenceResponse update (UUID idGeofence, GeofenceRequest request){
            var foundGeo = this.geofenceRepository.findById(idGeofence)
                .orElseThrow(NoSuchElementException::new);

        foundGeo.setLatitude(request.getLatitude());
        foundGeo.setLongitude(request.getLongitude());
        foundGeo.setRadius(request.getRadius());
        foundGeo.setZona(request.getZona());
        foundGeo.setTipoTransicao(request.getTipoTransicao());
        var foundFi = this.filialRepository.findById(request.getIdFilial())
                .orElseThrow(NoSuchElementException::new);
        foundGeo.setFilial(foundFi);

        var update = this.geofenceRepository.save(foundGeo);

        return this.toGeofenceResponse(update);
    }

    @Transactional
    public void delete (UUID idGeofence){
        this.geofenceRepository.findById(idGeofence)
                .orElseThrow(NoSuchElementException::new);
        this.geofenceRepository.deleteById(idGeofence);
    }

    public Geofence getById (UUID idGeofence){
        return this.geofenceRepository.findById(idGeofence)
                .orElseThrow(NoSuchElementException::new);
    }

    private GeofenceResponse toGeofenceResponse(Geofence geofence){
        return GeofenceResponse.builder()
                .idGeofence(geofence.getIdGeofence())
                .latitude(geofence.getLatitude())
                .longitude(geofence.getLongitude())
                .radius(geofence.getRadius())
                .zona(geofence.getZona())
                .tipoTransicao(geofence.getTipoTransicao())
                .idFilial(geofence.getFilial().getId())
                .build();
    }

}
