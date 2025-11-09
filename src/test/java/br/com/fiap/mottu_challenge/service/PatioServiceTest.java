package br.com.fiap.mottu_challenge.service;

import br.com.fiap.mottu_challenge.dto.request.PatioRequest;
import br.com.fiap.mottu_challenge.dto.response.PatioResponse;
import br.com.fiap.mottu_challenge.model.Filial;
import br.com.fiap.mottu_challenge.model.Patio;
import br.com.fiap.mottu_challenge.repository.FilialRepository;
import br.com.fiap.mottu_challenge.repository.PatioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PatioServiceTest {

    @Mock
    private PatioRepository patioRepository;

    @Mock
    private FilialRepository filialRepository;

    @InjectMocks
    private PatioService patioService;

    private UUID patioId;
    private UUID filialId;
    private Filial filial;
    private Patio patio;
    private PatioRequest patioRequest;

    @BeforeEach
    void setUp() {
        filialId = UUID.randomUUID();
        patioId = UUID.randomUUID();

        filial = new Filial();
        filial.setId(filialId);
        filial.setNome("Filial Teste");

        patio = Patio.builder()
                .idPatio(patioId)
                .nome("Patio Central")
                .descricao("Descricao patio")
                .flagAberto("S")
                .filial(filial)
                .timestampCreated(LocalDateTime.now())
                .build();

        patioRequest = new PatioRequest();
        patioRequest.setNome("Patio Central");
        patioRequest.setDescricao("Descricao patio");
        patioRequest.setFlagAberto("S");
        patioRequest.setIdFilial(filialId);
    }

    @Test
    @DisplayName("Deve criar um patio com sucesso quando a filial existir")
    void create_WhenFilialExists_ShouldSaveAndReturnPatioResponse() {
        when(filialRepository.findById(filialId)).thenReturn(Optional.of(filial));
        when(patioRepository.save(any(Patio.class))).thenReturn(patio);

        PatioResponse response = patioService.create(patioRequest);

        assertNotNull(response);
        assertEquals(patioId, response.getIdPatio());
        assertEquals("Patio Central", response.getNome());
        assertEquals(filialId, response.getIdFilial());

        verify(filialRepository).findById(filialId);
        verify(patioRepository).save(any(Patio.class));
    }

    @Test
    @DisplayName("Deve lançar RuntimeException (embrulhando NoSuchElementException) ao criar patio com filial inexistente")
    void create_WhenFilialNotFound_ShouldThrowRuntimeException() {
        when(filialRepository.findById(filialId)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            patioService.create(patioRequest);
        });

        assertTrue(exception.getMessage().contains("Filial não encontrada"));
        verify(filialRepository).findById(filialId);
        verify(patioRepository, never()).save(any(Patio.class));
    }

    @Test
    @DisplayName("Deve realizar um soft delete (setar flagAberto para 'N') ao invés de deletar")
    void delete_WhenPatioExists_ShouldPerformSoftDelete() {
        when(patioRepository.findById(patioId)).thenReturn(Optional.of(patio));
        when(patioRepository.save(any(Patio.class))).thenReturn(patio);

        patioService.delete(patioId);

        verify(patioRepository).findById(patioId);
        verify(patioRepository).save(patio);
        assertEquals("N", patio.getFlagAberto());
    }

    @Test
    @DisplayName("Deve lançar NoSuchElementException ao buscar por ID um patio que não está aberto (ou não existe)")
    void findById_WhenPatioNotOpenOrFound_ShouldThrowNoSuchElementException() {
        when(patioRepository.findByIdPatioAndFlagAberto(patioId, "S")).thenReturn(null);

        assertThrows(NoSuchElementException.class, () -> {
            patioService.findById(patioId);
        });

        verify(patioRepository).findByIdPatioAndFlagAberto(patioId, "S");
    }
}