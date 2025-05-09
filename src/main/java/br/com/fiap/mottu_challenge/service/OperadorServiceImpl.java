package br.com.fiap.mottu_challenge.service;

import br.com.fiap.mottu_challenge.dto.request.OperadorRequest;
import br.com.fiap.mottu_challenge.dto.response.OperadorResponse;
import br.com.fiap.mottu_challenge.model.Endereco;
import br.com.fiap.mottu_challenge.model.Operador;
import br.com.fiap.mottu_challenge.repository.OperadorRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;

@Service
public class OperadorServiceImpl implements OperadorService {

    @Autowired
    private OperadorRepository operadorRepository;

    @Transactional
    @Override
    public ResponseEntity<List<OperadorResponse>> create(List<OperadorRequest> operadorList) {
        boolean hasNonNullId = operadorList.stream()
                .anyMatch(operador -> operador.getId() != null);
        if (hasNonNullId) {
            throw new IllegalArgumentException("A lista contém operadores com ID não nulo.");
        }
        var operadores = operadorList.stream()
                .map(this::operadorMapper)
                .toList();
        var saved = this.operadorRepository.saveAll(operadores);
        var mappedResponse = saved.stream()
                .map(this::toOperadorResponse)
                .toList();
        return ResponseEntity.ok(mappedResponse);
    }

    @Override
    public ResponseEntity<OperadorResponse> getById(UUID id) {
        if(id == null) {
            throw new IllegalArgumentException("Operador nulo..");
        }
        var found = this.operadorRepository.findById(id);
        if (found.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }
        Operador operador = found.get();
        return ResponseEntity.ok(toOperadorResponse(operador));
    }

    @Override
    public void deleteById(UUID id) {
        if(id == null) {
            throw new IllegalArgumentException("Operador nulo..");
        }
        this.operadorRepository.deleteById(id);
    }

    @Override
    public ResponseEntity<OperadorResponse> updateById(UUID id, OperadorRequest operadorRequest) {
        if(id == null) {
            throw new IllegalArgumentException("Id do operador esta nulo..");
        }
        var found = this.operadorRepository.findById(id);
        if (found.isEmpty()) {
            throw new NoSuchElementException();
        }
        Operador operador = found.get();
        operador.setNome(operadorRequest.getNome());
        operador.setCpf(operadorRequest.getCpf());
        operador.setRg(operadorRequest.getRg());
        operador.setDataNascimento(operadorRequest.getDataNascimento());
        var updated = this.operadorRepository.save(operador);
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
                .idOperador(request.getId())
                .nome(request.getNome())
                .rg(request.getRg())
                .cpf(request.getCpf())
                .dataNascimento(request.getDataNascimento())
                .build();
    }
}
