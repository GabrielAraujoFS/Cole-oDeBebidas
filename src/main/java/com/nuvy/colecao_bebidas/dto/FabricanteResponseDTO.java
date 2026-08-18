package com.nuvy.colecao_bebidas.dto;

public record FabricanteResponseDTO(
        Long id,
        String nome,
        String historia,
        String origemRegiao
) {}