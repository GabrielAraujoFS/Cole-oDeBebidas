package com.nuvy.colecao_bebidas.dto;

import jakarta.validation.constraints.NotBlank;

public record FabricanteRequestDTO(
        @NotBlank String nome,
        String historia,
        String origemRegiao
) {}