package br.com.fiap.mottu_challenge.service;

import br.com.fiap.mottu_challenge.dto.request.EnderecoRequest;
import br.com.fiap.mottu_challenge.dto.request.EnderecoRequestList;
import br.com.fiap.mottu_challenge.dto.response.EnderecoResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.UUID;

public interface EnderecoService {
    ResponseEntity<List<EnderecoResponse>> create(@Valid @RequestBody EnderecoRequestList input);
    ResponseEntity<EnderecoResponse> getById(UUID id);
    void delete(UUID id);
    ResponseEntity<EnderecoResponse> update(UUID id, @Valid @RequestBody EnderecoRequest input);

}
