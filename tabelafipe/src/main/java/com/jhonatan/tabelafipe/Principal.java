package com.jhonatan.tabelafipe;

import com.jhonatan.tabelafipe.model.*;
import com.jhonatan.tabelafipe.service.ConsumoApi;
import com.jhonatan.tabelafipe.service.ConverterDados;

import java.util.*;

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

        List<Carros> carros;
        List<Motos> motos;
        List<Caminhoes> caminhoes;

        var json = consumoApi.BuscaApi("https://parallelum.com.br/fipe/api/v1/" + tipoAutomovel + "/marcas");
        System.out.println(json);
        ConverterDados converterDados = new ConverterDados();
        DadosConvertidosMarcas[] dadosConvertidosMarcas = converterDados.Conversor(json, DadosConvertidosMarcas[].class);

        if (tipoAutomovel.contains("carr")) {
            carros = Arrays.stream(dadosConvertidosMarcas).map(m -> new Carros(m.nome(), m.codigo())).toList();
            carros.stream().sorted(Comparator.comparing(Carros::getCodigo)).forEach(c -> System.out.println("Cód: " + c.getCodigo() + " Descrição: " + c.getNome()));
        } else if (tipoAutomovel.contains("mot")) {
            motos = Arrays.stream(dadosConvertidosMarcas).map(m -> new Motos(m.nome(), m.codigo())).toList();
            motos.stream().sorted(Comparator.comparing(Motos::getCodigo)).forEach(c -> System.out.println("Cód: " + c.getCodigo() + " Descrição: " + c.getNome()));
        } else if (tipoAutomovel.contains("cam")) {
            caminhoes = Arrays.stream(dadosConvertidosMarcas).map(m -> new Caminhoes(m.nome(), m.codigo())).toList();
            caminhoes.stream().sorted(Comparator.comparing(Caminhoes::getCodigo)).forEach(c -> System.out.println("Cód: " + c.getCodigo() + " Descrição: " + c.getNome()));
        } else {
            System.out.println("Erro! veiculo não encontrado!");
        }


        System.out.println("Informe o nome ou o código da marca: ");
        var entrada = leitura.nextLine().trim().toUpperCase();

        Optional<DadosConvertidosMarcas> marcaEncotrada = Arrays.stream(dadosConvertidosMarcas)
                .filter(m -> m.nome().toUpperCase().contains(entrada))
                .findFirst();

        if (entrada.matches("\\d+")) {
            json = consumoApi.BuscaApi("https://parallelum.com.br/fipe/api/v1/" + tipoAutomovel + "/marcas/" + entrada + "/modelos");
        } else if (marcaEncotrada.isPresent()) {
            String codicoMarca = marcaEncotrada.get().codigo();
            json = consumoApi.BuscaApi("https://parallelum.com.br/fipe/api/v1/"  + tipoAutomovel + "/marcas/" + codicoMarca + "/modelos");
        } else {
            System.out.println("Marca não encotrada! Digite os dados corretamente!");
        }

        System.out.println(json);
        DadosConvertidosModelos dadosConvertidosModelos = converterDados.Conversor(json, DadosConvertidosModelos.class);
        dadosConvertidosModelos.modelos().forEach(m -> System.out.println("Cód: " + m.codigo() + " Descrição: " + m.nome()));

        System.out.println("Digite um trecho do nome do modelo que deseja visualizar: ");
        var trechoDoModelo = leitura.nextLine().trim().toUpperCase();
        dadosConvertidosModelos.modelos().stream()
                .filter(m -> m.nome().toUpperCase().contains(trechoDoModelo))
                .forEach(m -> System.out.println("Cód: " + m.codigo() + " Descrição: " + m.nome()));
    }
}
