package com.nuvy.colecao_bebidas.dto;
import java.util.Map;
public record DashboardDTO (
        long totalItens,
        Map<String, Long> porTipo,
        Map<String, Long> porEstado
){}
