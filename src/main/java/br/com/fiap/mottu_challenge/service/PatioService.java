package br.com.fiap.mottu_challenge.service;

import br.com.fiap.mottu_challenge.dto.request.PatioRequest;
import br.com.fiap.mottu_challenge.dto.response.PatioResponse;
import br.com.fiap.mottu_challenge.model.Filial;
import br.com.fiap.mottu_challenge.model.Patio;
import br.com.fiap.mottu_challenge.repository.FilialRepository;
import br.com.fiap.mottu_challenge.repository.PatioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class PatioService {

    @Autowired
    private PatioRepository patioRepository;
    @Autowired
    private FilialRepository filialRepository;

    @Transactional
    public PatioResponse create(PatioRequest request){
        var idFilial = request.getIdFilial();
        var found = filialRepository.findById(idFilial)
                .orElseThrow(NoSuchElementException::new);

        var patio = new Patio();
        patio.setNome(request.getNome());
        patio.setFlagAberto(request.getFlagAberto());
        patio.setDescricao(request.getDescricao());
        patio.setTimestampCreated(LocalDateTime.now());
        patio.setTimestampUpdated(null);
        patio.setFilial(found);
        var saved = this.patioRepository.save(patio);
        return toPatioResponse(saved);
    }


    @Transactional
    public PatioResponse update(UUID idPatio, PatioRequest request){
        var foundPatio = this.patioRepository.findById(idPatio)
                .orElseThrow(NoSuchElementException::new);
        foundPatio.setNome(request.getNome());
        foundPatio.setFlagAberto(request.getFlagAberto());
        foundPatio.setDescricao(request.getDescricao());
        foundPatio.setTimestampUpdated(LocalDateTime.now());
        var saved = this.patioRepository.save(foundPatio);
        return toPatioResponse(saved);
    }

    @Transactional
    public void delete(UUID idPatio){
        var foundPatio = this.patioRepository.findById(idPatio)
                .orElseThrow(NoSuchElementException::new);
        foundPatio.setFlagAberto(false);
        this.patioRepository.save(foundPatio);
    }

    public Patio findById(UUID idPatio){
        var foundPatio = this.patioRepository.findByIdPatioAndFlagAbertoTrue(idPatio);
        if(foundPatio == null){
            throw new NoSuchElementException();
        }
        return foundPatio;

    }

    private PatioResponse toPatioResponse(Patio patio){
        return PatioResponse.builder()
                .idPatio(patio.getIdPatio())
                .nome(patio.getNome())
                .descricao(patio.getDescricao())
                .flagAberto(patio.getFlagAberto())
                .idFilial(patio.getFilial().getId())
                .build();
    }
}
