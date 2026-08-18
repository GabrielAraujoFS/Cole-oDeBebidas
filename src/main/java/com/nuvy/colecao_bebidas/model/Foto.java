package com.nuvy.colecao_bebidas.model;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Foto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String url;

    private Boolean principal;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;
}
