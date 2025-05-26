package br.com.fiap.mottu_challenge.service;

import br.com.fiap.mottu_challenge.dto.request.PatioEventoRequest;
import br.com.fiap.mottu_challenge.dto.response.PatioEventoResponse;
import br.com.fiap.mottu_challenge.model.Patio;
import br.com.fiap.mottu_challenge.model.PatioEvento;
import br.com.fiap.mottu_challenge.model.Sensor;
import br.com.fiap.mottu_challenge.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PatioEventoService {

    @Autowired
    private PatioEventoRepository patioEventoRepository;
    @Autowired
    private SensorRepository sensorRepository;
    @Autowired
    private PatioRepository patioRepository;

    @Transactional
    public PatioEventoResponse create (PatioEventoRequest request){
        var patioEvento = new PatioEvento();
        patioEvento.setZona(request.getZona());
        patioEvento.setLatitude(request.getLatitude());
        patioEvento.setLongitude(request.getLongitude());
        patioEvento.setTimestampEvento(LocalDateTime.now());
        patioEvento.setSensor(getSensor(request.getIdSensor()));
        patioEvento.setPatio(getPatio(request.getIdPatio()));
        var saved = patioEventoRepository.save(patioEvento);
        return this.toPatioEventoResponse(saved);
    }

    @Transactional
    public PatioEventoResponse findById(UUID idPatioEvento){
        var foundPatioEvento = getPatioEvento(idPatioEvento);
        return this.toPatioEventoResponse(foundPatioEvento);
    }

    @Transactional
    public void delete(UUID idGeofence){
        this.patioEventoRepository.findById(idGeofence)
                .orElseThrow(NoSuchElementException::new);
        this.patioEventoRepository.deleteById(idGeofence);
    }

    public PatioEvento getById(UUID idGeofence){
        return this.patioEventoRepository.findById(idGeofence)
                .orElseThrow(NoSuchElementException::new);
    }

    private PatioEventoResponse toPatioEventoResponse(PatioEvento patioEvento){
        return PatioEventoResponse.builder()
                .idPatioEvento(patioEvento.getIdPatioEvento())
                .latitude(patioEvento.getLatitude())
                .longitude(patioEvento.getLongitude())
                .zona(patioEvento.getZona())
                .tipoEvento(patioEvento.getTipoEvento())
                .idPatio(patioEvento.getPatio().getIdPatio())
                .idSensor(patioEvento.getSensor().getIdSensor())
                .build();
    }

    public List<PatioEventoResponse> findAll(){
        var patioEventos = patioEventoRepository.findAll();
        if(patioEventos.isEmpty()){
            throw new NoSuchElementException();
        }
        return patioEventos.stream().map(this::toPatioEventoResponse).toList();
    }

    private PatioEvento getPatioEvento (UUID id){
        return patioEventoRepository.findById(id)
                .orElseThrow(NoSuchElementException::new);
    }
    private Sensor getSensor (UUID id){
        return sensorRepository.findById(id)
                .orElseThrow(NoSuchElementException::new);
    }
    private Patio getPatio (UUID id){
        return patioRepository.findById(id)
                .orElseThrow(NoSuchElementException::new);
    }
}
