package com.jhonatan.tabelafipe.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosConvertidosMarcas(String nome, String codigo) {
}
