package com.nuvy.colecao_bebidas.service;

import com.nuvy.colecao_bebidas.dto.FabricanteRequestDTO;
import com.nuvy.colecao_bebidas.dto.FabricanteResponseDTO;
import com.nuvy.colecao_bebidas.model.Fabricante;
import com.nuvy.colecao_bebidas.repository.FabricanteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FabricanteService {

    private final FabricanteRepository fabricanteRepository;

    public FabricanteService(FabricanteRepository fabricanteRepository) {
        this.fabricanteRepository = fabricanteRepository;
    }

    public List<FabricanteResponseDTO> listarTodos() {
        return fabricanteRepository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    public FabricanteResponseDTO buscarPorId(Long id) {
        Fabricante fabricante = fabricanteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Fabricante não encontrado com id: " + id));
        return toResponseDTO(fabricante);
    }

    public FabricanteResponseDTO criar(FabricanteRequestDTO dto) {
        Fabricante fabricante = new Fabricante();
        preencherFabricante(fabricante, dto);
        Fabricante salvo = fabricanteRepository.save(fabricante);
        return toResponseDTO(salvo);
    }

    public FabricanteResponseDTO atualizar(Long id, FabricanteRequestDTO dto) {
        Fabricante fabricante = fabricanteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Fabricante não encontrado com id: " + id));
        preencherFabricante(fabricante, dto);
        Fabricante atualizado = fabricanteRepository.save(fabricante);
        return toResponseDTO(atualizado);
    }

    public void deletar(Long id) {
        if (!fabricanteRepository.existsById(id)) {
            throw new RuntimeException("Fabricante não encontrado com id: " + id);
        }
        fabricanteRepository.deleteById(id);
    }

    private void preencherFabricante(Fabricante fabricante, FabricanteRequestDTO dto) {
        fabricante.setNome(dto.nome());
        fabricante.setHistoria(dto.historia());
        fabricante.setOrigemRegiao(dto.origemRegiao());
    }

    private FabricanteResponseDTO toResponseDTO(Fabricante fabricante) {
        return new FabricanteResponseDTO(
                fabricante.getId(),
                fabricante.getNome(),
                fabricante.getHistoria(),
                fabricante.getOrigemRegiao()
        );
    }
}