package br.com.fiap.mottu_challenge.service;

import br.com.fiap.mottu_challenge.dto.request.SensorRequest;
import br.com.fiap.mottu_challenge.dto.response.SensorResponse;
import br.com.fiap.mottu_challenge.model.Sensor;
import br.com.fiap.mottu_challenge.repository.SensorRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.NoSuchElementException;
import java.util.UUID;


@Service
@AllArgsConstructor
public class SensorService {

    @Autowired
    private SensorRepository sensorRepository;

    @Transactional
    public SensorResponse save(SensorRequest request) {
        if(request.getIdSensor() != null) {
            throw new IllegalArgumentException();
        }
        var sensor = new Sensor();
        sensor.setFirmware(request.getFirmware());
        sensor.setTipo(request.getTipo());
        sensor.setDataHoraAtualizacao(request.getDataHoraAtualizacao());
        var saved = sensorRepository.save(sensor);
        return this.toSensorResponse(saved);
    }

    @Transactional
    public SensorResponse update(@PathVariable UUID id, SensorRequest request) {
        if(request.getIdSensor() == null || id == null) {
            throw new IllegalArgumentException();
        }
        var found = this.sensorRepository.findById(id).orElseThrow(
                NoSuchElementException::new);
        Sensor sensor = new Sensor();
        sensor.setTipo(found.getTipo());
        sensor.setFirmware(found.getFirmware());
        sensor.setDataHoraAtualizacao(found.getDataHoraAtualizacao());
        var saved = this.sensorRepository.save(sensor);
        return this.toSensorResponse(saved);
    }

    public Sensor getById(UUID id) {
        return this.sensorRepository.findById(id).orElse(null);
    }

    public void deleteById(UUID id) {
        var found = this.sensorRepository.findById(id);
        if(found.isEmpty()) {
            throw new NoSuchElementException();
        }
        this.sensorRepository.deleteById(id);
    }

    public SensorResponse toSensorResponse(Sensor sensor) {
        return SensorResponse.builder()
                .idSensor(sensor.getIdSensor())
                .tipoSensor(sensor.getTipo())
                .firmware(sensor.getFirmware())
                .dataHoraAtualizacao(sensor.getDataHoraAtualizacao())
                .build();
    }
}
