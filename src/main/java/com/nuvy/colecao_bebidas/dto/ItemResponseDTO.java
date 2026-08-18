package com.nuvy.colecao_bebidas.dto;

import com.nuvy.colecao_bebidas.enums.EstadoConservacao;
import com.nuvy.colecao_bebidas.enums.TipoRecipiente;
import java.time.LocalDate;
import java.util.List;

public record ItemResponseDTO(
        Long id,
        String nome,
        TipoRecipiente tipo,
        Integer volumeMl,
        EstadoConservacao estado,
        String fabricanteNome,
        String origem,
        String observacoes,
        LocalDate dataAquisicao,
        List<String> fotoUrls
) {}