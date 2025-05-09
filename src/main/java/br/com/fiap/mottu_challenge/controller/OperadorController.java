package br.com.fiap.mottu_challenge.controller;

import br.com.fiap.mottu_challenge.dto.request.OperadorRequest;
import br.com.fiap.mottu_challenge.dto.request.OperadorRequestList;
import br.com.fiap.mottu_challenge.dto.response.OperadorResponse;
import br.com.fiap.mottu_challenge.service.OperadorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/operador")
public class OperadorController {

    @Autowired
    private OperadorService operadorService;

    @PostMapping
    public ResponseEntity<List<OperadorResponse>> create(@Valid  @RequestBody OperadorRequestList operadorList) {
        var saved = this.operadorService.create(operadorList.getOperadorList());
        return ResponseEntity.ok(saved.getBody());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OperadorResponse> getById(@PathVariable UUID id) {
        var found = this.operadorService.getById(id);
        return ResponseEntity.ok(found.getBody());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id) {
        this.operadorService.deleteById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OperadorResponse> update(@PathVariable UUID id, @RequestBody OperadorRequest operadorRequest) {
        var updated = this.operadorService.updateById(id, operadorRequest);
        return ResponseEntity.ok(updated.getBody());
    }
}
