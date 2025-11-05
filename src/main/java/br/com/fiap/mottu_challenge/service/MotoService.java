package br.com.fiap.mottu_challenge.service;

import br.com.fiap.mottu_challenge.dto.request.MotoRequest;
import br.com.fiap.mottu_challenge.dto.response.MotoResponse;
import br.com.fiap.mottu_challenge.model.Moto;
import br.com.fiap.mottu_challenge.model.Operador;
import br.com.fiap.mottu_challenge.model.Patio;
import br.com.fiap.mottu_challenge.model.enums.Modelo;
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
        moto.setStatus(request.getStatus());
        moto.setSetor(request.getSetor());
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
        foundMoto.setStatus(request.getStatus());
        foundMoto.setSetor(request.getSetor());
        foundMoto.setPatio(getPatio(request.getIdPatio()));
        foundMoto.setOperador(getOperador(request.getIdOperador()));
        var updatedMoto = this.motoRepository.save(foundMoto);
        return this.motoToResponse(updatedMoto);
    }

    @Transactional
    public void delete(UUID uuid) {
        // CORREÇÃO: O método agora deleta de verdade, em vez de salvar sem fazer nada.
        // O ideal seria verificar se a moto existe antes, mas para simplificar,
        // vamos usar o deleteById que já faz isso.
        this.motoRepository.deleteById(uuid);
    }

    public Moto getById(UUID idMoto) {
        // CORREÇÃO: Trocamos o método antigo "findByIdMotoAndFlagAtivoTrue"
        // pelo findById padrão, que é o correto agora.
        return this.motoRepository.findById(idMoto)
            .orElseThrow(() -> new NoSuchElementException("Moto não encontrada com o ID: " + idMoto));
    }

    public List<Moto> getByModelo(Modelo modelo) {
        var findAll = this.motoRepository.findAll();
        return findAll.stream()
                .filter(moto -> moto.getModelo().equals(modelo))
                .toList();
    }

    public List<MotoResponse> findAll() {
        List<Moto> motos = this.motoRepository.findAll();
        return motos.stream()
                .map(this::motoToResponse)
                .toList();
    }

    public List<MotoResponse> findByPatioId(UUID idPatio) {
        return this.motoRepository.findByPatioIdPatio(idPatio)
                .stream()
                .map(this::motoToResponse) // Reutiliza seu mapper
                .toList();
    }

    private MotoResponse motoToResponse(Moto moto) {
        return MotoResponse.builder()
                .idMoto(moto.getIdMoto())
                .modelo(moto.getModelo())
                .placa(moto.getPlaca())
                .chassi(moto.getChassi())
                .status(moto.getStatus())
                .setor(moto.getSetor())
                .idOperador(moto.getOperador() != null ? moto.getOperador().getIdOperador() : null)
                .idPatio(moto.getPatio() != null ? moto.getPatio().getIdPatio() : null)
                .build();
    }

    private Moto getMoto(UUID uuid) {
        return this.motoRepository.findById(uuid)
                .orElseThrow(() -> new NoSuchElementException("Moto não encontrada com o ID: " + uuid));
    }

    private Operador getOperador(UUID uuid) {
        if (uuid == null) {
            return null;
        }
        return this.operadorRepository.findById(uuid)
                .orElseThrow(NoSuchElementException::new);
    }

    public Patio getPatio(UUID uuid) {
        if (uuid == null) {
            return null;
        }
        return this.patioRepository.findById(uuid)
                .orElseThrow(NoSuchElementException::new);
    }
}
