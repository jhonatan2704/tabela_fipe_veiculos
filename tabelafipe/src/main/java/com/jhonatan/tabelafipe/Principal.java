package com.jhonatan.tabelafipe;

import com.jhonatan.tabelafipe.model.DadosConvertidosMarcas;
import com.jhonatan.tabelafipe.model.DadosConvertidosModelos;
import com.jhonatan.tabelafipe.service.ConsumoApi;
import com.jhonatan.tabelafipe.service.ConverterDados;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

public class Principal {
    public void inicializarSistema() {
        Scanner leitura = new Scanner(System.in);
        System.out.println("""
				****Opções****
				Carro
				Moto
				Caminhão""");
        System.out.println("Digite uma das opções para consultar valores:");
        var tipoAutomovel = leitura.nextLine();
        ConsumoApi consumoApi = new ConsumoApi();
        if (tipoAutomovel.contains("carr")) {
            tipoAutomovel = "carros";
        } else if (tipoAutomovel.contains("mot")) {
            tipoAutomovel = "motos";
        } else if (tipoAutomovel.contains("cam")) {
            tipoAutomovel = "caminhoes";
        } else {
            throw new RuntimeException("Tipo de veículo não encontrado!");
        }

        var json = consumoApi.BuscaApi("https://parallelum.com.br/fipe/api/v1/" + tipoAutomovel + "/marcas");
        System.out.println(json);
        ConverterDados converterDados = new ConverterDados();
        DadosConvertidosMarcas[] dadosConvertidosMarcas = converterDados.Conversor(json, DadosConvertidosMarcas[].class);
        Arrays.stream(dadosConvertidosMarcas)
                .sorted(Comparator.comparing(DadosConvertidosMarcas::codigo))
                .forEach(d -> System.out.println("Cód: " + d.codigo() + " Descrição: " + d.nome()));

        System.out.println("Informe o código da marca para consulta:");
        var codigoDaMarca = leitura.nextLine();
        json = consumoApi.BuscaApi("https://parallelum.com.br/fipe/api/v1/" + tipoAutomovel + "/marcas/" + codigoDaMarca + "/modelos");
        System.out.println(json);
        DadosConvertidosModelos dadosConvertidosModelos = converterDados.Conversor(json, DadosConvertidosModelos.class);
        dadosConvertidosModelos.modelos().forEach(m -> System.out.println("Cód: " + m.codigo() + " Descrição: " + m.nome()));

        System.out.println("Digite um trecho do nome do modelo que deseja visualizar: ");
        var trechoDoModelo = leitura.nextLine();
        dadosConvertidosModelos.modelos().stream()
                .filter(m -> m.nome().contains(trechoDoModelo))
                .forEach(m -> System.out.println("Cód: " + m.codigo() + " Descrição: " + m.nome()));
    }
}
