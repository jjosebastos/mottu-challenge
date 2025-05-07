package br.com.fiap.mottu_challenge.controller;

import br.com.fiap.mottu_challenge.dto.request.EnderecoRequest;
import br.com.fiap.mottu_challenge.dto.request.EnderecoRequestList;
import br.com.fiap.mottu_challenge.dto.response.EnderecoResponse;
import br.com.fiap.mottu_challenge.service.EnderecoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/endereco")
public class EnderecoController {

    @Autowired
    private EnderecoService enderecoService;

    @PostMapping
    public ResponseEntity<List<EnderecoResponse>> post(@RequestBody @Valid EnderecoRequestList endereco) {
        var created = this.enderecoService.create(endereco);
        return ResponseEntity.ok(created.getBody());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EnderecoResponse> get(@PathVariable UUID id) {
        var found = this.enderecoService.getById(id);
        return ResponseEntity.ok(found.getBody());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void  delete(@PathVariable UUID id) {
        this.enderecoService.delete(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EnderecoResponse> put(@PathVariable UUID id,
                                                @RequestBody @Valid EnderecoRequest endereco) {
        var updated = this.enderecoService.update(id, endereco);
        return ResponseEntity.ok(updated.getBody());
    }


}
