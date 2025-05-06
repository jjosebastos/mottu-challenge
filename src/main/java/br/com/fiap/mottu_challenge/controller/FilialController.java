package br.com.fiap.mottu_challenge.controller;
import br.com.fiap.mottu_challenge.dto.request.FilialRequest;
import br.com.fiap.mottu_challenge.dto.request.FilialRequestList;
import br.com.fiap.mottu_challenge.dto.response.FilialResponse;
import br.com.fiap.mottu_challenge.service.FilialService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/filial")
public class FilialController {


    @Autowired
    private FilialService service;

    @PostMapping
    public ResponseEntity<List<FilialResponse>> create(@Valid @RequestBody FilialRequestList input) {
        var created = service.create(input);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(created.getBody());
    }

    @PutMapping("/{id}")
    public ResponseEntity<FilialResponse> update(@PathVariable UUID id, @Valid @RequestBody FilialRequest input) {
        var updated = service.update(id, input);
        return ResponseEntity.ok(updated.getBody());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<FilialResponse> delete(@PathVariable UUID id) {
        this.service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<FilialResponse> get(@PathVariable UUID id) {
        var found = service.getById(id);
        return ResponseEntity.ok(found.getBody());
    }
}
