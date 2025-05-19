package br.com.fiap.mottu_challenge.service;

import br.com.fiap.mottu_challenge.dto.request.ManutencaoRequest;
import br.com.fiap.mottu_challenge.dto.response.ManutencaoResponse;
import br.com.fiap.mottu_challenge.model.Manutencao;
import br.com.fiap.mottu_challenge.model.Moto;
import br.com.fiap.mottu_challenge.model.Sensor;
import br.com.fiap.mottu_challenge.repository.ManutencaoRepository;
import br.com.fiap.mottu_challenge.repository.MotoRepository;
import br.com.fiap.mottu_challenge.repository.SensorRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class ManutencaoService {

    @Autowired
    private ManutencaoRepository manutencaoRepository;
    @Autowired
    private MotoRepository motoRepository;
    @Autowired
    private SensorRepository sensorRepository;

    @Transactional
    public ManutencaoResponse save(ManutencaoRequest request) {
        var manutencao = new Manutencao();
        manutencao.setTipo(request.getTipo());
        manutencao.setDescricao(request.getDescricao());
        manutencao.setStatus(request.getStatus());
        manutencao.setFlagAtivo(true);
        manutencao.setTimestampCreated(LocalDateTime.now());
        manutencao.setMoto(getMoto(request.getIdMoto()));
        manutencao.setSensor(getSensor(request.getIdSensor()));
        var saved = this.manutencaoRepository.save(manutencao);
        return toManutencaoResponse(saved);
    }

    @Transactional
    public ManutencaoResponse update(UUID idManutencao, ManutencaoRequest request) {
        var foundManutencao = getManutencao(idManutencao);
        foundManutencao.setTipo(request.getTipo());
        foundManutencao.setDescricao(request.getDescricao());
        foundManutencao.setStatus(request.getStatus());
        foundManutencao.setTimestampUpdated(LocalDateTime.now());
        foundManutencao.setMoto(getMoto(request.getIdMoto()));
        foundManutencao.setSensor(getSensor(request.getIdSensor()));
        var saved = this.manutencaoRepository.save(foundManutencao);
        return toManutencaoResponse(saved);
    }

    private ManutencaoResponse toManutencaoResponse(Manutencao manutencao) {
        return ManutencaoResponse.builder()
                .idManutencao(manutencao.getIdManutencao())
                .tipo(manutencao.getTipo())
                .descricao(manutencao.getDescricao())
                .status(manutencao.getStatus())
                .idMoto(manutencao.getMoto() != null ? manutencao.getMoto().getId() : null)
                .idSensor(manutencao.getSensor() != null ? manutencao.getSensor().getIdSensor() : null)
                .build();
    }

    @Transactional
    public void delete(UUID idManutencao) {
        var foundManutencao = getManutencao(idManutencao);
        foundManutencao.setFlagAtivo(false);
        this.manutencaoRepository.save(foundManutencao);
    }

    public Manutencao findById(UUID idManutencao) {
        return getManutencao(idManutencao);
    }

    private Manutencao getManutencao(UUID id) {
        return manutencaoRepository.findById(id).orElseThrow(NoSuchElementException::new);
    }

    private Moto getMoto(UUID id) {
        if(id == null) return null;
        return motoRepository.findById(id).orElseThrow(NoSuchElementException::new);
    }

    private Sensor getSensor(UUID id) {
        if(id == null) return null;
        return sensorRepository.findById(id).orElseThrow(NoSuchElementException::new);
    }
}
