package com.nuvy.colecao_bebidas.dto;

import com.nuvy.colecao_bebidas.enums.EstadoConservacao;
import com.nuvy.colecao_bebidas.enums.TipoRecipiente;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

public record ItemRequestDTO(
        @NotBlank String nome,
        @NotNull TipoRecipiente tipo,
        Integer volumeMl,
        @NotNull EstadoConservacao estado,
        Long fabricanteId,
        String origem,
        String observacoes,
        LocalDate dataAquisicao,
        BigDecimal valorAquisicao
) {}