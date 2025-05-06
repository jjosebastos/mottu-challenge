package br.com.fiap.mottu_challenge.service;

import br.com.fiap.mottu_challenge.dto.request.FilialRequest;
import br.com.fiap.mottu_challenge.dto.request.FilialRequestList;
import br.com.fiap.mottu_challenge.dto.response.FilialResponse;
import br.com.fiap.mottu_challenge.model.Filial;
import br.com.fiap.mottu_challenge.model.enums.CodPais;
import br.com.fiap.mottu_challenge.repository.FilialRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class FilialServiceImpl implements FilialService {

    @Autowired
    private FilialRepository repository;

    @Transactional
    @Override
    public ResponseEntity<List<FilialResponse>> create(FilialRequestList input) {
        var filialRequest = input.getFilialRequests();
        if (filialRequest.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        var novasFiliais = filialRequest.stream()
                .filter(fil -> fil.getIdFilial() == null)
                .map(this::filialMapper)
                .toList();
        var created = repository.saveAll(novasFiliais);
        var response = created.stream()
                .map(this::toFilialResponse)
                .toList();

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Override
    public ResponseEntity<FilialResponse> update(UUID id, FilialRequest input) {
        if (id == null && input.getIdFilial() == null) {
            return ResponseEntity.badRequest().build();
        }
        var found = this.repository.findById(id);
        if (found.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Filial filial = found.get();
        filial.setDataAbertura(input.getDataAbertura());
        filial.setCnpj(input.getCnpj());
        filial.setNome(input.getNome());
        filial.setCodPais(input.getCdPais());
        var updated = repository.save(filial);

        return ResponseEntity.ok(toFilialResponse(updated));
    }

    @Override
    public void delete(UUID id) {
        if (id == null) {
            throw new IllegalArgumentException("ID não pode ser nulo");
        }
        var found = this.repository.findById(id);
        if (found.isEmpty()) {
            throw new EntityNotFoundException("Filial não encontrada com o Id: " + id);
        }
        this.repository.deleteById(id);
    }

    @Override
    public ResponseEntity<FilialResponse> getById(UUID id) {
        if(id == null) {
            return ResponseEntity.badRequest().build();
        }
        return this.repository.findById(id)
                .map(filial -> ResponseEntity.ok(new FilialResponse(
                        filial.getId(),
                        filial.getNome(),
                        filial.getCnpj(),
                        filial.getCodPais(),
                        filial.getDataAbertura()
                )))
                .orElse(ResponseEntity.notFound().build());
    }

    private FilialResponse toFilialResponse(Filial request) {
        FilialResponse response = new FilialResponse();
        response.setIdFilial(request.getId());
        response.setNome(request.getNome());
        response.setCnpj(request.getCnpj());
        response.setDataAbertura(request.getDataAbertura());
        response.setCdPais(request.getCodPais());
        return response;
    }
    private Filial filialMapper(FilialRequest filMap) {
        Filial filial = new Filial();
        filial.setCnpj(filMap.getCnpj());
        filial.setNome(filMap.getNome());
        filial.setCodPais(filMap.getCdPais());
        filial.setDataAbertura(filMap.getDataAbertura());
        return filial;
    }
}
