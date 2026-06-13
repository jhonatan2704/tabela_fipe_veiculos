package com.jhonatan.tabelafipe.model;

public enum TipoVeiculo {
    CARRO("Carro"),
    MOTO("Moto"),
    CAMINHAO("Caminhao");

    private String marcaVeiculo;

    TipoVeiculo(String marcaVeiculo) {
        this.marcaVeiculo = marcaVeiculo;
    }

    public static TipoVeiculo fromString(String marcaVeiculo) {
        for (TipoVeiculo tipo : TipoVeiculo.values()) {
            if (tipo.marcaVeiculo.equalsIgnoreCase(marcaVeiculo)) {
                return tipo;
            }
        }
        throw new IllegalArgumentException("Veiculo não encotrado!");
    }
}
