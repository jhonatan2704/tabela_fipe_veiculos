package com.jhonatan.tabelafipe.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jhonatan.tabelafipe.model.ModeloVeiculo;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ModelosDto {
    private List<ModeloVeiculo> modelos;

    public List<ModeloVeiculo> getModelos() {
        return modelos;
    }

    public void setModelos(List<ModeloVeiculo> modelos) {
        this.modelos = modelos;
    }
}