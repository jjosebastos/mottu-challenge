package br.com.fiap.mottu_challenge.service;

import br.com.fiap.mottu_challenge.dto.request.FilialRequest;
import br.com.fiap.mottu_challenge.dto.request.FilialRequestList;
import br.com.fiap.mottu_challenge.dto.response.FilialResponse;
import br.com.fiap.mottu_challenge.model.Filial;
import br.com.fiap.mottu_challenge.repository.FilialRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class FilialService {

    @Autowired
    private FilialRepository repository;

    @Transactional
    public List<FilialResponse> create(FilialRequestList input) {
        var filialRequest = input.getFilialRequests();
        if (filialRequest.isEmpty()) {
            throw new IllegalArgumentException();
        }
        var novasFiliais = filialRequest.stream()
                .map(this::filialMapper)
                .toList();
        var created = repository.saveAll(novasFiliais);

        return created.stream()
                .map(this::toFilialResponse)
                .toList();
    }

    @Transactional
    public FilialResponse update(UUID id, FilialRequest input) {
        var found = this.repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(id.toString()));
        found.setDataAbertura(input.getDataAbertura());
        found.setCnpj(input.getCnpj());
        found.setNome(input.getNome());
        found.setCodPais(input.getCdPais());
        var updated = repository.save(found);

        return toFilialResponse(updated);
    }

    @Transactional
    public void delete(UUID id) {
        var found = this.repository.findById(id);
        if (found.isEmpty()) {
            throw new NoSuchElementException();
        }
        this.repository.deleteById(id);
    }

    public List<FilialResponse> findAll() {
        var filiais = this.repository.findAll();
        if (filiais.isEmpty()) {
            throw new NoSuchElementException();
        }
        return filiais.stream()
                .map(this::toFilialResponse)
                .toList();
    }

    public FilialResponse getById(UUID id) {
        return this.repository.findById(id)
                .map(this::toFilialResponse)
                .orElseThrow(NoSuchElementException::new);
    }

    private FilialResponse toFilialResponse(Filial filial) {
        return FilialResponse.builder()
                .idFilial(filial.getId())
                .nome(filial.getNome())
                .cnpj(filial.getCnpj())
                .dataAbertura(filial.getDataAbertura())
                .cdPais(filial.getCodPais())
                .build();
    }
    private Filial filialMapper(FilialRequest request) {
        return Filial.
                builder()
                .nome(request.getNome())
                .cnpj(request.getCnpj())
                .codPais(request.getCdPais())
                .dataAbertura(request.getDataAbertura())
                .build();
    }
}
