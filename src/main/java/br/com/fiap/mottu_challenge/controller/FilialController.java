package br.com.fiap.mottu_challenge.controller;
import br.com.fiap.mottu_challenge.dto.request.FilialRequestList;
import br.com.fiap.mottu_challenge.dto.response.FilialResponse;
import br.com.fiap.mottu_challenge.service.FilialService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/filial")
public class FilialController {


    @Autowired
    private FilialService service;

    @PostMapping
    public ResponseEntity<List<FilialResponse>> create(@Valid @RequestBody FilialRequestList input) {
        var created = service.create(input);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(created.getBody());
    }
}
