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
    private TipoVeiculo tipoVeiculo;
    private String nome;
    @OneToMany(mappedBy = "marca",  fetch = FetchType.EAGER)
    private List<ModeloVeiculo> modelos = new ArrayList<>();


    public MarcaVeiculo(String codigo, TipoVeiculo tipoVeiculo, String nome) {
        this.codigo = codigo;
        this.tipoVeiculo = tipoVeiculo;
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

    @Override
    public String toString() {
        return "Codigo = " + codigo +
                ", Tipo de veiculo = " + tipoVeiculo +
                ", Marca = " + nome +
                ", Modelos = " + modelos;
    }
}
