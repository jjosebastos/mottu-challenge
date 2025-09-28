package br.com.fiap.mottu_challenge.controller;

import br.com.fiap.mottu_challenge.dto.request.AtualizarStatusMotoRequest;
import br.com.fiap.mottu_challenge.dto.response.MotoPatioResponse;
import br.com.fiap.mottu_challenge.dto.response.SetorResponse;
import br.com.fiap.mottu_challenge.model.enums.Setor;
import br.com.fiap.mottu_challenge.service.PatioMappingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/patio-mapping")
@CrossOrigin(origins = "*")
public class PatioMappingController {

    @Autowired
    private PatioMappingService patioMappingService;

    @GetMapping("/setores")
    @Operation(summary = "Buscar todos os setores", description = "Retorna todos os setores com suas motos e estatísticas",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Setores encontrados com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Nenhum setor encontrado")
            })
    public ResponseEntity<List<SetorResponse>> getAllSetores() {
        List<SetorResponse> setores = patioMappingService.getAllSetores();
        return ResponseEntity.ok(setores);
    }

    @GetMapping("/setor/{setor}")
    @Operation(summary = "Buscar motos por setor", description = "Retorna todas as motos de um setor específico",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Motos encontradas com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Setor não encontrado")
            })
    public ResponseEntity<SetorResponse> getMotosBySetor(@PathVariable Setor setor) {
        SetorResponse setorResponse = patioMappingService.getMotosBySetor(setor);
        return ResponseEntity.ok(setorResponse);
    }

    @GetMapping("/moto/{id}")
    @Operation(summary = "Buscar detalhes da moto", description = "Retorna detalhes de uma moto específica",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Moto encontrada com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Moto não encontrada")
            })
    public ResponseEntity<MotoPatioResponse> getMotoDetails(@PathVariable UUID id) {
        MotoPatioResponse moto = patioMappingService.getMotoDetails(id);
        return ResponseEntity.ok(moto);
    }

    @PutMapping("/moto/{id}/status")
    @Operation(summary = "Atualizar status da moto", description = "Atualiza o status de uma moto específica",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Status atualizado com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Requisição inválida"),
                    @ApiResponse(responseCode = "404", description = "Moto não encontrada")
            })
    public ResponseEntity<MotoPatioResponse> updateMotoStatus(
            @PathVariable UUID id, 
            @RequestBody AtualizarStatusMotoRequest request) {
        MotoPatioResponse moto = patioMappingService.updateMotoStatus(id, request.getStatus());
        return ResponseEntity.ok(moto);
    }

    @GetMapping("/moto/placa/{placa}")
    @Operation(summary = "Buscar moto por placa", description = "Retorna detalhes de uma moto pela placa",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Moto encontrada com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Moto não encontrada")
            })
    public ResponseEntity<MotoPatioResponse> getMotoByPlaca(@PathVariable String placa) {
        MotoPatioResponse moto = patioMappingService.getMotoByPlaca(placa);
        return ResponseEntity.ok(moto);
    }
}
