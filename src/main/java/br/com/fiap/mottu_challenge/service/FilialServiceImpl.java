package br.com.fiap.mottu_challenge.service;

import br.com.fiap.mottu_challenge.dto.request.FilialRequest;
import br.com.fiap.mottu_challenge.dto.request.FilialRequestList;
import br.com.fiap.mottu_challenge.dto.response.FilialResponse;
import br.com.fiap.mottu_challenge.model.Filial;
import br.com.fiap.mottu_challenge.model.enums.CodPais;
import br.com.fiap.mottu_challenge.repository.FilialRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FilialServiceImpl implements FilialService {

    @Autowired
    private FilialRepository repository;

    @Transactional
    @Override
    public ResponseEntity<List<FilialResponse>> create(FilialRequestList input) {
        var filialRequest = input.getFilialRequests();
        if(filialRequest.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        var novasFiliais = filialRequest.stream()
                .filter(fil -> fil.getIdFilial() == null)
                .map(this::filialMapper)
                .toList();
        var created = repository.saveAll(novasFiliais);
        var response = created.stream()
                .map(this::toFilialResponse)
                .toList();

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    private FilialResponse toFilialResponse(Filial request) {
        FilialResponse response = new FilialResponse();
        response.setIdFilial(request.getId());
        response.setCnpj(request.getCnpj());
        response.setDataAbertura(request.getDataAbertura());
        response.setCdPais(CodPais.BRA.getCodPais());
        return response;
    }
    private Filial filialMapper(FilialRequest filMap) {
        Filial filial = new Filial();
        filial.setCnpj(filMap.getCnpj());
        filial.setNome(filMap.getNome());
        filial.setCodPais(CodPais.BRA);
        filial.setDataAbertura(filMap.getDataAbertura()); // corrigido!
        return filial;
    }
}
