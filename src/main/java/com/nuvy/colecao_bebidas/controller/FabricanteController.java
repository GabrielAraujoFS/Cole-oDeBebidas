package com.nuvy.colecao_bebidas.controller;

import com.nuvy.colecao_bebidas.dto.FabricanteRequestDTO;
import com.nuvy.colecao_bebidas.dto.FabricanteResponseDTO;
import com.nuvy.colecao_bebidas.service.FabricanteService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/fabricantes")
public class FabricanteController {

    private final FabricanteService fabricanteService;

    public FabricanteController(FabricanteService fabricanteService) {
        this.fabricanteService = fabricanteService;
    }

    @GetMapping
    public List<FabricanteResponseDTO> listarTodos() {
        return fabricanteService.listarTodos();
    }

    @GetMapping("/{id}")
    public FabricanteResponseDTO buscarPorId(@PathVariable Long id) {
        return fabricanteService.buscarPorId(id);
    }

    @PostMapping
    public ResponseEntity<FabricanteResponseDTO> criar(@Valid @RequestBody FabricanteRequestDTO dto) {
        FabricanteResponseDTO criado = fabricanteService.criar(dto);
        return ResponseEntity.status(201).body(criado);
    }

    @PutMapping("/{id}")
    public FabricanteResponseDTO atualizar(@PathVariable Long id, @Valid @RequestBody FabricanteRequestDTO dto) {
        return fabricanteService.atualizar(id, dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        fabricanteService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}