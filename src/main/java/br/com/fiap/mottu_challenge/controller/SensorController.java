package br.com.fiap.mottu_challenge.controller;

import br.com.fiap.mottu_challenge.dto.request.SensorRequest;
import br.com.fiap.mottu_challenge.dto.response.SensorResponse;
import br.com.fiap.mottu_challenge.model.Sensor;
import br.com.fiap.mottu_challenge.service.SensorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/sensor")
public class SensorController {

    @Autowired
    private SensorService service;

    @PostMapping
    public ResponseEntity<SensorResponse> createSensor(@Valid @RequestBody SensorRequest request) {
        var saved = this.service.save(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SensorResponse> updateSensor(@PathVariable UUID id, @Valid @RequestBody SensorRequest request) {
        var updated = this.service.update(id, request);
        return ResponseEntity.ok(updated);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Sensor> readSensor(@PathVariable UUID id) {
        var found = this.service.getById(id);
        if(found == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(found);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteSensor(@PathVariable UUID id) {
        this.service.deleteById(id);
    }
}
