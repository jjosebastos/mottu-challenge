package br.com.fiap.mottu_challenge.service;

import br.com.fiap.mottu_challenge.dto.request.FilialRequest;
import br.com.fiap.mottu_challenge.dto.request.FilialRequestList;
import br.com.fiap.mottu_challenge.dto.response.FilialResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

public interface FilialService {
    ResponseEntity<List<FilialResponse>> create(FilialRequestList input);
    ResponseEntity<FilialResponse> update(UUID id, FilialRequest input);
    void delete(UUID id);
    ResponseEntity<FilialResponse> getById(UUID id);
 }
