package br.com.fiap.mottu_challenge.service;

import br.com.fiap.mottu_challenge.dto.request.SensorRequest;
import br.com.fiap.mottu_challenge.dto.response.SensorResponse;
import br.com.fiap.mottu_challenge.model.Moto;
import br.com.fiap.mottu_challenge.model.Sensor;
import br.com.fiap.mottu_challenge.model.StatusSensor;
import br.com.fiap.mottu_challenge.repository.MotoRepository;
import br.com.fiap.mottu_challenge.repository.SensorRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.UUID;


@Service
public class SensorService {

    @Autowired
    private SensorRepository sensorRepository;
    @Autowired
    private MotoRepository motoRepository;

    @Transactional
    public SensorResponse save(SensorRequest request) {
        var sensor = new Sensor();
        toSensorObject(request, sensor);
        var saved = sensorRepository.save(sensor);
        return this.toSensorResponse(saved);
    }

    @Transactional
    public SensorResponse update(@PathVariable UUID id, SensorRequest request) {
        var foundSensor = getSensor(id);
        toSensorObject(request, foundSensor);
        var updatedSensor = this.sensorRepository.save(foundSensor);
        return this.toSensorResponse(updatedSensor);
    }

    @Transactional
    public void deleteById(UUID id) {
        var foundSensor = getSensor(id);
        foundSensor.setFlagAtivo(false);
        foundSensor.setStatusSensor(StatusSensor.DESATIVADO);
        this.sensorRepository.save(foundSensor);

    }

    public SensorResponse getById(UUID id) {
        var foundSensor = this.sensorRepository.findActiveById(id)
                .orElseThrow(NoSuchElementException::new);
        return toSensorResponse(foundSensor);
    }

    public SensorResponse toSensorResponse(Sensor sensor) {
        return SensorResponse.builder()
                .idSensor(sensor.getIdSensor())
                .modelo(sensor.getModelo())
                .fabricante(sensor.getFabricante())
                .statusSensor(sensor.getStatusSensor())
                .tipo(sensor.getTipo())
                .versaoFirmware(sensor.getVersaoFirmware())
                .dataInstalacao(sensor.getDataInstalacao())
                .dataCalibracao(sensor.getDataCalibracao())
                .lastSeen(sensor.getLastSeen())
                .signalStrength(sensor.getSignalStrength())
                .idMoto(sensor.getMoto().getIdMoto())
                .build();
    }

    public Moto getMoto(UUID id) {
        return motoRepository.findById(id).
                orElseThrow(NoSuchElementException::new);
    }

    public Sensor getSensor(UUID id) {
        return sensorRepository.findById(id)
                .orElseThrow(NoSuchElementException::new);
    }

    private void toSensorObject(SensorRequest request, Sensor foundSensor) {
        foundSensor.setTipo(request.getTipo());
        foundSensor.setModelo(request.getModelo());
        foundSensor.setFabricante(request.getFabricante());
        foundSensor.setStatusSensor(request.getStatusSensor());
        foundSensor.setVersaoFirmware(request.getVersaoFirmware());
        foundSensor.setDataInstalacao(request.getDataInstalacao());
        foundSensor.setDataCalibracao(request.getDataCalibracao());
        foundSensor.setLastSeen(LocalDateTime.now());
        foundSensor.setSignalStrength(request.getSignalStrength());
        foundSensor.setMoto(getMoto(request.getIdMoto()));
        foundSensor.setFlagAtivo(true);
    }
}
