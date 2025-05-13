package br.com.fiap.mottu_challenge.controller;


import br.com.fiap.mottu_challenge.dto.request.GeofenceRequest;
import br.com.fiap.mottu_challenge.dto.response.GeofenceResponse;
import br.com.fiap.mottu_challenge.model.Geofence;
import br.com.fiap.mottu_challenge.service.GeofenceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/geofence")
public class GeofenceController {

    @Autowired
    private GeofenceService geofenceService;

    @CacheEvict("geofences")
    @PostMapping
    @Operation(summary = "Cria Geofences", description = "Cria Geofences de filiais",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Geofence criado com sucesso."),
                    @ApiResponse(responseCode = "400", description = "Requisição inválida."),
                    @ApiResponse(responseCode = "404", description = "ID Geofence / ID Filial não encontrados.")
            })
    public ResponseEntity<GeofenceResponse> create(@Valid @RequestBody GeofenceRequest request){
        var saved = this.geofenceService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza Geofences", description = "Atualiza o Geofences com base no ID fornecido",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Geofences atualizado."),
                    @ApiResponse(responseCode = "400", description = "ID fornecido inválido."),
                    @ApiResponse(responseCode = "404", description = "ID Geofence / ID Filial não encontrados.")
            })
    public ResponseEntity<GeofenceResponse> update(@PathVariable UUID id, @Valid @RequestBody GeofenceRequest request){
        var saved = this.geofenceService.update(id, request);
        return ResponseEntity.status(HttpStatus.OK).body(saved);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Remove Geofences", description = "Remove o Geofences com base no ID fornecido.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Geofences removido com sucesso."),
                    @ApiResponse(responseCode = "400", description = "ID fornecido invalido."),
                    @ApiResponse(responseCode = "404", description = "Geofences não encontrada.")
            })
    public void deleteById(@PathVariable UUID id){
        this.geofenceService.delete(id);
    }

    @GetMapping("/{id}")
    @Cacheable("geofences")
    @Operation(summary = "Busca Geofences", description = "Busca Geofences com base no ID fornecido",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Encontrado com sucesso."),
                    @ApiResponse(responseCode = "400", description = "ID fornecido inválido."),
                    @ApiResponse(responseCode = "404", description = "Geofence não encontrado.")
            })
    public ResponseEntity<Geofence> getById(@PathVariable UUID id){
        var found = this.geofenceService.getById(id);
        return ResponseEntity.ok(found);
    }
}
