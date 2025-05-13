package br.com.fiap.mottu_challenge.controller;


import br.com.fiap.mottu_challenge.dto.request.GeofenceRequest;
import br.com.fiap.mottu_challenge.dto.response.GeofenceResponse;
import br.com.fiap.mottu_challenge.model.Geofence;
import br.com.fiap.mottu_challenge.service.GeofenceService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/geofence")
public class GeofenceController {

    @Autowired
    private GeofenceService geofenceService;

    @PostMapping
    public ResponseEntity<GeofenceResponse> create(@Valid @RequestBody GeofenceRequest request){
        var saved = this.geofenceService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GeofenceResponse> update(@PathVariable UUID id, @Valid @RequestBody GeofenceRequest request){
        var saved = this.geofenceService.update(id, request);
        return ResponseEntity.status(HttpStatus.OK).body(saved);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id){
        this.geofenceService.delete(id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Geofence> getById(@PathVariable UUID id){
        var found = this.geofenceService.getById(id);
        if(found == null){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(found);
    }
}
