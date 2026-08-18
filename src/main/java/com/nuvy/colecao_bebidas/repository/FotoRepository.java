package com.nuvy.colecao_bebidas.repository;

import com.nuvy.colecao_bebidas.model.Foto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FotoRepository extends JpaRepository<Foto, Long> {
}