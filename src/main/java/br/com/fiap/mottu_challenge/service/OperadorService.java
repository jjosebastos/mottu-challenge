package br.com.fiap.mottu_challenge.service;

import br.com.fiap.mottu_challenge.dto.request.OperadorRequest;
import br.com.fiap.mottu_challenge.dto.response.OperadorResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

public interface OperadorService {
    ResponseEntity<List<OperadorResponse>> create(List<OperadorRequest> operadorList);
    ResponseEntity<OperadorResponse> getById(UUID id);
    void deleteById(UUID id);
    ResponseEntity<OperadorResponse> updateById(UUID id, OperadorRequest operadorRequest);

}
