package com.jhonatan.tabelafipe.model;

public class ModeloVeiculo {
    private String codigo;
    private String nome;
    private TipoVeiculo tipo;

    public ModeloVeiculo(String codigo, String nome, TipoVeiculo tipo) {
        this.codigo = codigo;
        this.nome = nome;
        this.tipo = tipo;
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

    public TipoVeiculo getTipo() {
        return tipo;
    }

    public void setTipo(TipoVeiculo tipo) {
        this.tipo = tipo;
    }
}
