package br.com.fiap.mottu_challenge.service;

import br.com.fiap.mottu_challenge.dto.request.MotoRequest;
import br.com.fiap.mottu_challenge.dto.response.MotoResponse;
import br.com.fiap.mottu_challenge.model.Moto;
import br.com.fiap.mottu_challenge.model.enums.Modelo;
import br.com.fiap.mottu_challenge.repository.FilialRepository;
import br.com.fiap.mottu_challenge.repository.MotoRepository;
import br.com.fiap.mottu_challenge.repository.OperadorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MotoService {
    @Autowired
    private MotoRepository repository;
    @Autowired
    private OperadorRepository operadorRepository;
    @Autowired
    private FilialRepository filialRepository;

    public MotoResponse save(MotoRequest moto) {
        var foundOperador = this.operadorRepository.findById(moto.getIdOperador())
                .orElseThrow(NoSuchElementException::new);
        var foundFilial = this.filialRepository.findById(moto.getIdFilial())
                .orElseThrow(NoSuchElementException::new);
        var saved = new Moto();
        saved.setPlaca(moto.getPlaca());
        saved.setModelo(moto.getModelo());
        saved.setChassi(moto.getChassi());
        saved.setOperador(foundOperador);
        saved.setFilial(foundFilial);

        var savedMoto = this.repository.save(saved);
        return this.motoToResponse(savedMoto);
    }

    public MotoResponse update(UUID id, MotoRequest moto) {
        if(id == null) {
            throw new IllegalArgumentException();
        }
        var motoFound = this.repository.findById(id)
                .orElseThrow(NoSuchElementException::new);
        motoFound.setPlaca(moto.getPlaca());
        motoFound.setModelo(moto.getModelo());
        motoFound.setChassi(moto.getChassi());

        var operadorFound = this.operadorRepository.findById(moto.getIdOperador()).orElse(null);
        var filialFound = this.filialRepository.findById(moto.getIdFilial()).orElse(null);
        motoFound.setFilial(filialFound);
        motoFound.setOperador(operadorFound);
        var updatedMoto = this.repository.save(motoFound);
        return this.motoToResponse(updatedMoto);
    }

    public void delete(UUID uuid) {
        var found = this.repository.findById(uuid)
                .orElseThrow(NoSuchElementException::new);
        this.repository.deleteById(uuid);
    }

    public Moto getById(UUID uuid) {
        var found = this.repository.findById(uuid);
        return found.orElse(null);
    }

    public List<Moto> getByModelo(Modelo modelo) {
        if(Objects.isNull(modelo)) {
            throw new IllegalArgumentException();
        }
        var findAll = this.repository.findAll();
        return findAll.stream()
                .filter(moto -> moto.getModelo().equals(modelo))
                .toList();
    }

    private MotoResponse motoToResponse(Moto moto) {
        return MotoResponse.builder()
                .idMoto(moto.getId())
                .modelo(moto.getModelo())
                .placa(moto.getPlaca())
                .chassi(moto.getChassi())
                .idOperador(moto.getOperador().getIdOperador())
                .idFilial(moto.getFilial().getId())
                .build();
    }

}
