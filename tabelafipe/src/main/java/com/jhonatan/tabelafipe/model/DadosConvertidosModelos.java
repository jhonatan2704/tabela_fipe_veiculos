package com.jhonatan.tabelafipe.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosConvertidosModelos(List<DadosConvertidosMarcas> modelos) {
}
