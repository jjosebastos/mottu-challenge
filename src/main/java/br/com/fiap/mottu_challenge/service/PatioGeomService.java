package br.com.fiap.mottu_challenge.service;

import br.com.fiap.mottu_challenge.dto.request.PatioGeomRequest;
import br.com.fiap.mottu_challenge.dto.response.PatioGeomResponse;
import br.com.fiap.mottu_challenge.model.Patio;
import br.com.fiap.mottu_challenge.model.PatioGeom;
import br.com.fiap.mottu_challenge.repository.PatioGeomRepository;
import br.com.fiap.mottu_challenge.repository.PatioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PatioGeomService {

    @Autowired
    private PatioGeomRepository patioGeomRepository;
    @Autowired
    private PatioRepository patioRepository;

    @Transactional
    public PatioGeomResponse save(PatioGeomRequest request) {
        PatioGeom patioGeom = new PatioGeom();
        patioGeom.setLatitudeMin(request.getLatitudeMinima());
        patioGeom.setLongitudeMax(request.getLongitudeMaxima());
        patioGeom.setLatitudeMax(request.getLatitudeMaxima());
        patioGeom.setLongitudeMin(request.getLongitudeMinima());
        patioGeom.setPatio(getPatio(request.getIdPatio()));
        patioGeom.setFlagAtivo(true);
        var savedPatioGeom = this.patioGeomRepository.save(patioGeom);
        return toPatioGeomResponse(savedPatioGeom);
    }

    @Transactional
    public PatioGeomResponse update(UUID idPatioGeom, PatioGeomRequest request) {
        var patioGeomFound = getPatioGeom(idPatioGeom);
        patioGeomFound.setLatitudeMin(request.getLatitudeMinima());
        patioGeomFound.setLongitudeMin(request.getLongitudeMinima());
        patioGeomFound.setLatitudeMax(request.getLatitudeMaxima());
        patioGeomFound.setLongitudeMax(request.getLongitudeMaxima());
        patioGeomFound.setPatio(getPatio(request.getIdPatio()));
        var savedPatioGeom = this.patioGeomRepository.save(patioGeomFound);
        return toPatioGeomResponse(savedPatioGeom);
    }

    @Transactional
    public void delete(UUID idPatioGeom) {
        var patioGeomFound = getPatioGeom(idPatioGeom);
        patioGeomFound.setFlagAtivo(false);
        this.patioGeomRepository.save(patioGeomFound);
    }


    public PatioGeom findById(UUID idPatioGeom) {
        var foundPatioGeom = this.patioGeomRepository.findByIdPatioGeomAndFlagAtivoTrue(idPatioGeom);
        if (foundPatioGeom == null) {
            throw new NoSuchElementException();
        }
        return foundPatioGeom;
    }

    public List<PatioGeomResponse> findAll() {
        var patioGeom = this.patioGeomRepository.findAll();
        if(patioGeom.isEmpty()){
            throw new NoSuchElementException();
        }
        return patioGeom.stream().map(this::toPatioGeomResponse).toList();
    }

    private PatioGeomResponse toPatioGeomResponse(PatioGeom patioGeom) {
        return PatioGeomResponse.builder()
                .idPatioGeom(patioGeom.getIdPatioGeom())
                .latitudeMin(patioGeom.getLatitudeMin())
                .longitudeMax(patioGeom.getLongitudeMin())
                .latitudeMax(patioGeom.getLatitudeMax())
                .longitudeMin(patioGeom.getLongitudeMax())
                .idPatio(patioGeom.getPatio().getIdPatio())
                .build();
    }

    private PatioGeom getPatioGeom(UUID id) {
        return this.patioGeomRepository.findById(id)
                .orElseThrow(NoSuchElementException::new);
    }

    private Patio getPatio(UUID id) {
        return patioRepository.findById(id)
                .orElseThrow(NoSuchElementException::new);
    }
}
