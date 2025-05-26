package br.com.fiap.mottu_challenge.controller;

import br.com.fiap.mottu_challenge.dto.request.SensorRequest;
import br.com.fiap.mottu_challenge.dto.response.PatioEventoResponse;
import br.com.fiap.mottu_challenge.dto.response.SensorResponse;
import br.com.fiap.mottu_challenge.model.Sensor;
import br.com.fiap.mottu_challenge.service.SensorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/sensor")
public class SensorController {

    @Autowired
    private SensorService sensorService;

    @PostMapping
    @CacheEvict(value = "sensores", allEntries = true)
    @Operation(summary = "Cadastrar Sensor", description = "Insere um Sensor.", responses = {
            @ApiResponse(responseCode = "201", description = "Criada com sucesso."),
            @ApiResponse(responseCode = "400", description = "Requisição inválida"),
            @ApiResponse(responseCode = "404", description = "Registro não encontrado."),
    })
    public ResponseEntity<SensorResponse> createSensor(@Valid @RequestBody SensorRequest request) {
        var saved = this.sensorService.save(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar Sensor", description = "Atualiza registros de sensor com base no ID fornecido.", responses = {
            @ApiResponse(responseCode = "200", description = "Atualizado com sucesso."),
            @ApiResponse(responseCode = "400", description = "Requisição inválida."),
            @ApiResponse(responseCode = "404", description = "Registro não encontrado.")
    })
    public ResponseEntity<SensorResponse> putSensor(@PathVariable UUID id, @Valid @RequestBody SensorRequest request) {
        var updated = this.sensorService.update(id, request);
        return ResponseEntity.ok(updated);
    }



    @GetMapping("/{id}")
    @Cacheable(value = "sensor")
    @Operation(summary = "Buscar Manutenções", description = "Busca registros de Manutenção com base no ID fornecido.",  responses = {
            @ApiResponse(responseCode = "200", description = "Encontrado com sucesso."),
            @ApiResponse(responseCode = "400", description = "Requisição inválida."),
            @ApiResponse(responseCode = "404", description = "Registro não encontrado.")
    })
    public ResponseEntity<SensorResponse> readSensor(@PathVariable UUID id) {
        var found = this.sensorService.getById(id);
        return ResponseEntity.ok(found);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Remover sensores", description = "Atualiza a flag de sensor com base no ID fornecido.", responses = {
            @ApiResponse(responseCode = "204", description = "Removido com sucesso."),
            @ApiResponse(responseCode = "400", description = "Requisição inválida."),
            @ApiResponse(responseCode = "404", description = "Registro não encontrado.")
    })
    public void deleteSensor(@PathVariable UUID id) {
        this.sensorService.deleteById(id);
    }

    @GetMapping("/all")
    @Operation(summary = "Buscar Sensores", description = "Fazer a busca de todos os Sensores",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Encontrado com sucesso."),
                    @ApiResponse(responseCode = "404", description = "Nenhum registro encontrado.")
            }
    )
    public ResponseEntity<List<SensorResponse>> getAll() {
        var found = this.sensorService.findAll();
        return ResponseEntity.ok(found);
    }
}
