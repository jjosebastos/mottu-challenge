package br.com.fiap.mottu_challenge.service;

import br.com.fiap.mottu_challenge.dto.request.OperadorRequest;
import br.com.fiap.mottu_challenge.dto.response.OperadorResponse;
import br.com.fiap.mottu_challenge.model.Operador;
import br.com.fiap.mottu_challenge.repository.OperadorRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class OperadorService {

    @Autowired
    private OperadorRepository operadorRepository;

    @Transactional
    public List<OperadorResponse> create(List<OperadorRequest> operadorList) {
        var operadores = operadorList.stream()
                .map(this::operadorMapper)
                .toList();
        var saved = this.operadorRepository.saveAll(operadores);
        return saved.stream()
                .map(this::toOperadorResponse)
                .toList();
    }

    public Operador getById(UUID id) {
        return this.operadorRepository.findById(id)
                .orElseThrow(NoSuchElementException::new);
    }

    @Transactional
    public void deleteById(UUID id) {
        this.operadorRepository.findById(id)
                .orElseThrow(NoSuchElementException::new);
        this.operadorRepository.deleteById(id);
    }

    @Transactional
    public ResponseEntity<OperadorResponse> updateById(UUID id, OperadorRequest operadorRequest) {
        var found = this.operadorRepository.findById(id)
                .orElseThrow();
        found.setNome(operadorRequest.getNome());
        found.setCpf(operadorRequest.getCpf());
        found.setRg(operadorRequest.getRg());
        found.setDataNascimento(operadorRequest.getDataNascimento());
        var updated = this.operadorRepository.save(found);
        var response = this.toOperadorResponse(updated);
        return ResponseEntity.ok(response);
    }


    private OperadorResponse toOperadorResponse(Operador operador) {
        return OperadorResponse.builder()
                .id(operador.getIdOperador())
                .nome(operador.getNome())
                .rg(operador.getRg())
                .cpf(operador.getCpf())
                .dataNascimento(operador.getDataNascimento())
                .build();
    }

    private Operador operadorMapper(OperadorRequest request) {
        return Operador.builder()
                .nome(request.getNome())
                .rg(request.getRg())
                .cpf(request.getCpf())
                .dataNascimento(request.getDataNascimento())
                .build();
    }
}
