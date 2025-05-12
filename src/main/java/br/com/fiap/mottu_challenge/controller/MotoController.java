package br.com.fiap.mottu_challenge.controller;

import br.com.fiap.mottu_challenge.dto.request.MotoRequest;
import br.com.fiap.mottu_challenge.dto.response.MotoResponse;
import br.com.fiap.mottu_challenge.model.Moto;
import br.com.fiap.mottu_challenge.model.enums.Modelo;
import br.com.fiap.mottu_challenge.service.MotoService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/moto")
public class MotoController {

    @Autowired
    private MotoService service;
    @PostMapping
    public ResponseEntity<MotoResponse> moto(@RequestBody MotoRequest moto) {
        var saved = this.service.save(moto);
        return ResponseEntity.ok(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MotoResponse> moto(@PathVariable UUID id, @RequestBody MotoRequest moto) {
        var updated = this.service.update(id, moto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MotoResponse> moto(@PathVariable UUID id) {
        this.service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Moto> findById(@PathVariable UUID id) {
        var foundId = this.service.getById(id);
        if (foundId == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(foundId);
    }

    @GetMapping("/modelo/{modelo}")
    public ResponseEntity<List<Moto>> findByModelo(@PathVariable Modelo modelo) {
        var foundModelo = this.service.getByModelo(modelo);
        return ResponseEntity.ok(foundModelo);
    }

}

