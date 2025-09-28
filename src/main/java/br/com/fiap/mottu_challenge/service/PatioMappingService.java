package br.com.fiap.mottu_challenge.service;

import br.com.fiap.mottu_challenge.dto.response.MotoPatioResponse;
import br.com.fiap.mottu_challenge.dto.response.SetorResponse;
import br.com.fiap.mottu_challenge.model.Moto;
import br.com.fiap.mottu_challenge.model.enums.Setor;
import br.com.fiap.mottu_challenge.model.enums.StatusMoto;
import br.com.fiap.mottu_challenge.repository.MotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PatioMappingService {

    @Autowired
    private MotoRepository motoRepository;

    public List<SetorResponse> getAllSetores() {
        return List.of(Setor.values()).stream()
                .map(this::getMotosBySetor)
                .collect(Collectors.toList());
    }

    public SetorResponse getMotosBySetor(Setor setor) {
        List<Moto> motos = motoRepository.findBySetorAndFlagAtivoTrue(setor);
        
        List<MotoPatioResponse> motosResponse = motos.stream()
                .map(this::convertToMotoPatioResponse)
                .collect(Collectors.toList());

        int motosLivres = (int) motos.stream().filter(m -> m.getStatus() == StatusMoto.LIVRE).count();
        int motosManutencao = (int) motos.stream().filter(m -> m.getStatus() == StatusMoto.MANUTENCAO).count();
        int motosProblema = (int) motos.stream().filter(m -> m.getStatus() == StatusMoto.PROBLEMA).count();

        return SetorResponse.builder()
                .setor(setor)
                .nome(setor.getNome())
                .motos(motosResponse)
                .totalMotos(motos.size())
                .motosLivres(motosLivres)
                .motosManutencao(motosManutencao)
                .motosProblema(motosProblema)
                .build();
    }

    public MotoPatioResponse getMotoDetails(UUID id) {
        Moto moto = motoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Moto não encontrada"));
        
        return convertToMotoPatioResponse(moto);
    }

    public MotoPatioResponse getMotoByPlaca(String placa) {
        Moto moto = motoRepository.findByPlacaAndFlagAtivoTrue(placa)
                .orElseThrow(() -> new RuntimeException("Moto não encontrada"));
        
        return convertToMotoPatioResponse(moto);
    }

    public MotoPatioResponse updateMotoStatus(UUID id, StatusMoto novoStatus) {
        Moto moto = motoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Moto não encontrada"));
        
        moto.setStatus(novoStatus);
        motoRepository.save(moto);
        
        return convertToMotoPatioResponse(moto);
    }

    private MotoPatioResponse convertToMotoPatioResponse(Moto moto) {
        return MotoPatioResponse.builder()
                .idMoto(moto.getIdMoto())
                .placa(moto.getPlaca())
                .status(moto.getStatus())
                .setor(moto.getSetor())
                .cor(moto.getStatus() != null ? moto.getStatus().getCor() : "Cinza")
                .build();
    }
}
