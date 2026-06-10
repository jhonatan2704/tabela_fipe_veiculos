package com.jhonatan.tabelafipe.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class MarcaVeiculo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String codigo;
    @Column(nullable = false)
    private String nome;
    @OneToMany(mappedBy = "marca",  fetch = FetchType.EAGER)
    private List<ModeloVeiculo> modelos = new ArrayList<>();


    public MarcaVeiculo(String codigo, String nome) {
        this.codigo = codigo;
        this.nome = nome;
    }

    public MarcaVeiculo() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<ModeloVeiculo> getModelos() {
        return modelos;
    }

    public void setModelos(List<ModeloVeiculo> modelos) {
        this.modelos = modelos;
    }
}
