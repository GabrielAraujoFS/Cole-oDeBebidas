package com.nuvy.colecao_bebidas.model;

import com.nuvy.colecao_bebidas.enums.EstadoConservacao;
import com.nuvy.colecao_bebidas.enums.TipoRecipiente;
import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @Enumerated(EnumType.STRING)
    private TipoRecipiente tipo;

    private Integer volumeMl;

    @Enumerated(EnumType.STRING)
    private EstadoConservacao estado;

    @ManyToOne
    @JoinColumn(name = "fabricante_id")
    private Fabricante fabricante;

    private String origem;

    @Column(columnDefinition = "TEXT")
    private String observacoes;

    private LocalDate dataAquisicao;

    private BigDecimal valorAquisicao;

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Foto> fotos = new ArrayList<>();

    private LocalDateTime criadoEm;

    @PrePersist
    public void prePersist() {
        this.criadoEm = LocalDateTime.now();
    }
}
