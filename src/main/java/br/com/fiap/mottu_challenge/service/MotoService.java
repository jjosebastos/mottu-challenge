package br.com.fiap.mottu_challenge.service;

import br.com.fiap.mottu_challenge.dto.request.MotoRequest;
import br.com.fiap.mottu_challenge.dto.response.ManutencaoResponse;
import br.com.fiap.mottu_challenge.dto.response.MotoResponse;
import br.com.fiap.mottu_challenge.model.Moto;
import br.com.fiap.mottu_challenge.model.Operador;
import br.com.fiap.mottu_challenge.model.Patio;
import br.com.fiap.mottu_challenge.model.enums.Modelo;
import br.com.fiap.mottu_challenge.repository.FilialRepository;
import br.com.fiap.mottu_challenge.repository.MotoRepository;
import br.com.fiap.mottu_challenge.repository.OperadorRepository;
import br.com.fiap.mottu_challenge.repository.PatioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MotoService {
    @Autowired
    private MotoRepository motoRepository;
    @Autowired
    private OperadorRepository operadorRepository;
    @Autowired
    private PatioRepository patioRepository;

    @Transactional
    public MotoResponse save(MotoRequest request) {
        var moto = new Moto();
        moto.setPlaca(request.getPlaca());
        moto.setModelo(request.getModelo());
        moto.setChassi(request.getChassi());
        moto.setFlagAtivo(true);
        moto.setOperador(getOperador(request.getIdOperador()));
        moto.setPatio(getPatio(request.getIdPatio()));
        var savedMoto = this.motoRepository.save(moto);
        return this.motoToResponse(savedMoto);
    }

    @Transactional
    public MotoResponse update(UUID id, MotoRequest request) {
        var foundMoto = getMoto(id);
        foundMoto.setPlaca(request.getPlaca());
        foundMoto.setModelo(request.getModelo());
        foundMoto.setChassi(request.getChassi());
        foundMoto.setPatio(getPatio(request.getIdPatio()));
        foundMoto.setOperador(getOperador(request.getIdOperador()));
        var updatedMoto = this.motoRepository.save(foundMoto);
        return this.motoToResponse(updatedMoto);
    }

    @Transactional
    public void delete(UUID uuid) {
        var foundMoto = getMoto(uuid);
        foundMoto.setFlagAtivo(false);
        this.motoRepository.save(foundMoto);
    }

    public Moto getById(UUID idMoto) {
        return this.motoRepository.findByIdMotoAndFlagAtivoTrue(idMoto);
    }

    public List<Moto> getByModelo(Modelo modelo) {
        var findAll = this.motoRepository.findAll();
        return findAll.stream()
                .filter(moto -> moto.getModelo().equals(modelo))
                .toList();
    }

    public List<MotoResponse> findAll(){
        var manutencoes = this.motoRepository.findAll();
        if(manutencoes.isEmpty()){
            throw new NoSuchElementException();
        }
        return manutencoes.stream().map(this::motoToResponse).toList();
    }

    private MotoResponse motoToResponse(Moto moto) {
        return MotoResponse.builder()
                .idMoto(moto.getIdMoto())
                .modelo(moto.getModelo())
                .placa(moto.getPlaca())
                .chassi(moto.getChassi())
                .idOperador(moto.getOperador() != null ? moto.getOperador().getIdOperador() : null)
                .idPatio(moto.getPatio().getIdPatio())
                .build();
    }

    private Moto getMoto(UUID uuid) {
        return this.motoRepository.findById(uuid)
                .orElseThrow(NoSuchElementException::new);
    }

    private Operador getOperador(UUID uuid) {
        if(uuid == null) {
            return null;
        }
        return this.operadorRepository.findById(uuid)
                .orElseThrow(NoSuchElementException::new);
    }

    public Patio getPatio(UUID uuid) {
        if(uuid == null) {
            return null;
        }
        return this.patioRepository.findById(uuid)
                .orElseThrow(NoSuchElementException::new);
    }
}
