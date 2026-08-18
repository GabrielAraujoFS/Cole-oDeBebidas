package com.nuvy.colecao_bebidas.controller;

import com.nuvy.colecao_bebidas.dto.*;
import com.nuvy.colecao_bebidas.enums.EstadoConservacao;
import com.nuvy.colecao_bebidas.enums.TipoRecipiente;
import com.nuvy.colecao_bebidas.service.ItemService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.nuvy.colecao_bebidas.enums.EstadoConservacao;
import com.nuvy.colecao_bebidas.enums.TipoRecipiente;
import java.util.List;

@RestController
@RequestMapping("/api/itens")
public class ItemController {

    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping
    public List<ItemResponseDTO> listarTodos() {
        return itemService.listarTodos();
    }

    @GetMapping("/{id}")
    public ItemResponseDTO buscarPorId(@PathVariable Long id) {
        return itemService.buscarPorId(id);
    }

    @PostMapping
    public ResponseEntity<ItemResponseDTO> criar(@Valid @RequestBody ItemRequestDTO dto) {
        ItemResponseDTO criado = itemService.criar(dto);
        return ResponseEntity.status(201).body(criado);
    }

    @PutMapping("/{id}")
    public ItemResponseDTO atualizar(@PathVariable Long id, @Valid @RequestBody ItemRequestDTO dto) {
        return itemService.atualizar(id, dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        itemService.deletar(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/filtrar")
    public List<ItemResponseDTO> filtrar(
            @RequestParam(required = false) EstadoConservacao estado,
            @RequestParam(required = false) TipoRecipiente tipo
    ) {
        return itemService.filtrar(estado, tipo);
    }
    @GetMapping("/dashboard")
    public DashboardDTO dashboard(){
        return itemService.obterDashboard();
    }
    @PostMapping("/{id}/valor")
    public ValorAquisicaoDTO obterValor(@PathVariable Long id, @RequestBody ValidarSenhaDTO dto) {
        return itemService.obterValorProtegido(id, dto.senha());
    }

}