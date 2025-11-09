package br.com.fiap.mottu_challenge.service;

import br.com.fiap.mottu_challenge.dto.request.MotoRequest;
import br.com.fiap.mottu_challenge.dto.response.MotoResponse;
import br.com.fiap.mottu_challenge.model.Moto;
import br.com.fiap.mottu_challenge.model.Operador;
import br.com.fiap.mottu_challenge.model.Patio;
import br.com.fiap.mottu_challenge.model.enums.Modelo;
import br.com.fiap.mottu_challenge.model.enums.Setor;
import br.com.fiap.mottu_challenge.model.enums.StatusMoto;
import br.com.fiap.mottu_challenge.repository.MotoRepository;
import br.com.fiap.mottu_challenge.repository.OperadorRepository;
import br.com.fiap.mottu_challenge.repository.PatioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MotoServiceTest {

    @Mock
    private MotoRepository motoRepository;
    @Mock
    private OperadorRepository operadorRepository;
    @Mock
    private PatioRepository patioRepository;

    @InjectMocks
    private MotoService motoService;

    private UUID motoId;
    private UUID patioId;
    private UUID operadorId;
    private Moto moto;
    private MotoRequest motoRequest;
    private Patio patio;
    private Operador operador;

    @BeforeEach
    void setUp() {
        motoId = UUID.randomUUID();
        patioId = UUID.randomUUID();
        operadorId = UUID.randomUUID();

        patio = new Patio();
        patio.setIdPatio(patioId);

        operador = new Operador();
        operador.setIdOperador(operadorId);

        moto = Moto.builder()
                .idMoto(motoId)
                .placa("ABC1234")
                .modelo(Modelo.MOTTUPOP)
                .chassi("123CHASSI")
                .status(StatusMoto.LIVRE)
                .setor(Setor.A)
                .patio(patio)
                .operador(operador)
                .build();

        motoRequest = new MotoRequest();
        motoRequest.setPlaca("ABC1234");
        motoRequest.setModelo(Modelo.MOTTUPOP);
        motoRequest.setChassi("123CHASSI");
        motoRequest.setStatus(StatusMoto.LIVRE);
        motoRequest.setSetor(Setor.A);
        motoRequest.setIdPatio(patioId);
        motoRequest.setIdOperador(operadorId);
    }

    @Test
    @DisplayName("Deve salvar uma moto e retornar MotoResponse")
    void save_WhenGivenValidRequest_ShouldSaveAndReturnMotoResponse() {
        when(patioRepository.findById(patioId)).thenReturn(Optional.of(patio));
        when(operadorRepository.findById(operadorId)).thenReturn(Optional.of(operador));
        when(motoRepository.save(any(Moto.class))).thenReturn(moto);
        
        MotoResponse response = motoService.save(motoRequest);

        assertNotNull(response);
        assertEquals(motoId, response.getIdMoto());
        assertEquals("ABC1234", response.getPlaca());
        assertEquals(patioId, response.getIdPatio());
        assertEquals(operadorId, response.getIdOperador());

        verify(patioRepository).findById(patioId);
        verify(operadorRepository).findById(operadorId);
        verify(motoRepository).save(any(Moto.class));
    }

    @Test
    @DisplayName("Deve retornar uma moto quando o ID existir")
    void getById_WhenMotoExists_ShouldReturnMoto() {
        when(motoRepository.findById(motoId)).thenReturn(Optional.of(moto));

        Moto result = motoService.getById(motoId);

        assertNotNull(result);
        assertEquals(motoId, result.getIdMoto());
        assertEquals("ABC1234", result.getPlaca());
        verify(motoRepository).findById(motoId);
    }

    @Test
    @DisplayName("Deve lançar NoSuchElementException quando o ID não existir")
    void getById_WhenMotoDoesNotExist_ShouldThrowNoSuchElementException() {
        UUID idInexistente = UUID.randomUUID();
        when(motoRepository.findById(idInexistente)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> {
            motoService.getById(idInexistente);
        });

        verify(motoRepository).findById(idInexistente);
    }

    @Test
    @DisplayName("Deve chamar o deleteById do repositório ao deletar")
    void delete_WhenCalled_ShouldInvokeRepositoryDeleteById() {
        doNothing().when(motoRepository).deleteById(motoId);

        assertDoesNotThrow(() -> {
            motoService.delete(motoId);
        });

        verify(motoRepository, times(1)).deleteById(motoId);
    }
}