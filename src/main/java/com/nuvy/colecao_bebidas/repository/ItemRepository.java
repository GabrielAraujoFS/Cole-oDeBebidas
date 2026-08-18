package com.nuvy.colecao_bebidas.repository;

import com.nuvy.colecao_bebidas.enums.EstadoConservacao;
import com.nuvy.colecao_bebidas.enums.TipoRecipiente;
import com.nuvy.colecao_bebidas.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findByEstado(EstadoConservacao estado );
    List<Item> findByTipo(TipoRecipiente tipo);
    List<Item> findByEstadoAndTipo(EstadoConservacao estado, TipoRecipiente tipo);
}