package com.nuvy.colecao_bebidas.model;

import jakarta.persistence.*;
import lombok.Data;
import com.nuvy.colecao_bebidas.model.Item;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Fabricante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @Column(columnDefinition = "TEXT")
    private String historia;

    private String origemRegiao;

    @OneToMany(mappedBy = "fabricante")
    private List<Item> itens = new ArrayList<>();
}