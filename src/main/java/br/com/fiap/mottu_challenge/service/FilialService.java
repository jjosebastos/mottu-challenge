package br.com.fiap.mottu_challenge.service;

import br.com.fiap.mottu_challenge.dto.request.FilialRequest;
import br.com.fiap.mottu_challenge.dto.request.FilialRequestList;
import br.com.fiap.mottu_challenge.dto.response.FilialResponse;
import br.com.fiap.mottu_challenge.model.Filial;
import br.com.fiap.mottu_challenge.repository.FilialRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.slf4j.Logger; // <-- 1. IMPORTE O LOGGER
import org.slf4j.LoggerFactory; // <-- 2. IMPORTE O LOGGER FACTORY
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class FilialService {

    @Autowired
    private FilialRepository repository;

    @Autowired
    private ExpoNotificationService notificationService;

    // Logger para o try-catch
    private static final Logger logger = LoggerFactory.getLogger(FilialService.class);

    @Transactional
    public List<FilialResponse> create(FilialRequestList input) {
        var filialRequest = input.getFilialRequests();
        if (filialRequest.isEmpty()) {
            throw new IllegalArgumentException();
        }
        var novasFiliais = filialRequest.stream()
                .map(this::filialMapper)
                .toList();
        
        // Salva as filiais no banco (operação principal)
        var created = repository.saveAll(novasFiliais);

        // ==== ✨ 4. CHAME O SERVIÇO DE NOTIFICAÇÃO (SEGUNDO PLANO) ✨ ====
        try {
            int count = novasFiliais.size();
            String title = "Nova Filial Cadastrada!";
            
            // Formata a mensagem dependendo se foi 1 ou várias filiais
            String body = (count == 1) 
                ? "Uma nova filial (" + novasFiliais.get(0).getNome() + ") foi adicionada!" 
                : count + " novas filiais foram cadastradas!";
            
            // O @Async no método fará com que isso rode em segundo plano
            notificationService.sendNotificationToAll(title, body);

        } catch (Exception e) {
            // Se o envio da notificação falhar, nós apenas registramos o erro.
            // A operação principal (criar a filial) NÃO é revertida.
            logger.error("A filial foi criada, mas falhou ao enviar a notificação.", e);
        }
        // =======================================================

        return created.stream()
                .map(this::toFilialResponse)
                .toList();
    }

    @Transactional
    public FilialResponse update(UUID id, FilialRequest input) {
        var found = this.repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(id.toString()));
        found.setDataAbertura(input.getDataAbertura());
        found.setCnpj(input.getCnpj());
        found.setNome(input.getNome());
        found.setCodPais(input.getCdPais());
        var updated = repository.save(found);

        return toFilialResponse(updated);
    }

    @Transactional
    public void delete(UUID id) {
        var found = this.repository.findById(id);
        if (found.isEmpty()) {
            throw new NoSuchElementException();
        }
        this.repository.deleteById(id);
    }

    public List<FilialResponse> findAll() {
        var filiais = this.repository.findAll();
        return filiais.stream()
                .map(this::toFilialResponse)
                .toList();
    }

    public FilialResponse getById(UUID id) {
        return this.repository.findById(id)
                .map(this::toFilialResponse)
                .orElseThrow(NoSuchElementException::new);
    }

    private FilialResponse toFilialResponse(Filial filial) {
        return FilialResponse.builder()
                .idFilial(filial.getId())
                .nome(filial.getNome())
                .cnpj(filial.getCnpj())
                .dataAbertura(filial.getDataAbertura())
                .cdPais(filial.getCodPais())
                .build();
    }
    private Filial filialMapper(FilialRequest request) {
        return Filial.
                builder()
                .nome(request.getNome())
                .cnpj(request.getCnpj())
                .codPais(request.getCdPais())
                .dataAbertura(request.getDataAbertura())
                .build();
    }
}