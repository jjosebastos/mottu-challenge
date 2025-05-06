package br.com.fiap.mottu_challenge.service;

import br.com.fiap.mottu_challenge.dto.request.FilialRequestList;
import br.com.fiap.mottu_challenge.dto.response.FilialResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface FilialService {
    ResponseEntity<List<FilialResponse>> create(FilialRequestList input);
}
