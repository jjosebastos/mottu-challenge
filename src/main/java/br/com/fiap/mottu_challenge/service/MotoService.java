package br.com.fiap.mottu_challenge.service;

import br.com.fiap.mottu_challenge.dto.request.MotoRequest;
import br.com.fiap.mottu_challenge.dto.response.MotoResponse;
import br.com.fiap.mottu_challenge.model.Moto;
import br.com.fiap.mottu_challenge.model.enums.Modelo;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

public interface MotoService {
    MotoResponse save(MotoRequest moto);
    MotoResponse update(UUID uuid, MotoRequest moto);
    void delete(UUID uuid);
    Moto getById(UUID uuid);
    List<Moto> getByModelo(Modelo modelo);
}
