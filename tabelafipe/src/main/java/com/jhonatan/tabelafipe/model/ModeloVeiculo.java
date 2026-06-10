package com.jhonatan.tabelafipe.model;

import jakarta.persistence.*;

@Entity
public class ModeloVeiculo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String codigo;
    @Column(nullable = false)
    private String modelo;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoVeiculo tipo;
    @ManyToOne
    @JoinColumn(name = "marca_id")
    private MarcaVeiculo marca;

    public ModeloVeiculo(String codigo, String modelo, TipoVeiculo tipo) {
        this.codigo = codigo;
        this.modelo = modelo;
        this.tipo = tipo;
    }

    public ModeloVeiculo() {}

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


    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public TipoVeiculo getTipo() {
        return tipo;
    }

    public void setTipo(TipoVeiculo tipo) {
        this.tipo = tipo;
    }

    public MarcaVeiculo getMarca() {
        return marca;
    }

    public void setMarca(MarcaVeiculo marca) {
        this.marca = marca;
    }

    @Override
    public String toString() {
        return modelo + " - ";
    }
}
