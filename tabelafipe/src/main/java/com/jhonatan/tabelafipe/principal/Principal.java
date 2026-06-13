package com.jhonatan.tabelafipe.principal;

import com.jhonatan.tabelafipe.exceptions.ExceptionApi;
import com.jhonatan.tabelafipe.exceptions.ExceptionConversao;
import com.jhonatan.tabelafipe.model.*;
import com.jhonatan.tabelafipe.repository.MarcaVeiculoRepository;
import com.jhonatan.tabelafipe.repository.ModeloVeicularRepository;
import com.jhonatan.tabelafipe.service.ConsumoApi;
import com.jhonatan.tabelafipe.service.ConverterDados;

import java.util.*;

public class Principal {
    Scanner leitura = new Scanner(System.in);
    private ModeloVeicularRepository modeloVeicularRepository;
    private MarcaVeiculoRepository marcaRepository;

    public Principal(ModeloVeicularRepository modeloVeicularRepository, MarcaVeiculoRepository marcaRepository) {
        this.modeloVeicularRepository = modeloVeicularRepository;
        this.marcaRepository = marcaRepository;
    }

    public void inicializarSistema() {
        var inicializar = 1;
        while (inicializar != 0) {
            System.out.println("""
                    1 - Buscar veiculo
                    2 - Listar veiculos consultadas
                    
                    0 - Finalizar sistema""");

            var tipoDeConsulta = leitura.nextInt();
            leitura.nextLine();

            if (tipoDeConsulta == 1) {
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
                    System.out.println("Tipo de veículo não encontrado!");
                    return;
                }

                List<ModeloVeiculo> veiculos;
                List<MarcaVeiculo> marcas;

                try {
                    var json = consumoApi.BuscaApi("https://parallelum.com.br/fipe/api/v1/" + tipoAutomovel + "/marcas");
                    System.out.println(json);
                    ConverterDados converterDados = new ConverterDados();
                    DadosConvertidosMarcas[] dadosConvertidosMarcas = converterDados.Conversor(json, DadosConvertidosMarcas[].class);
                    Arrays.stream(dadosConvertidosMarcas).forEach(c -> System.out.println("Cód: " + c.codigo() + " Descrição: " + c.nome()));
                    if (tipoAutomovel.contains("carr")) {
                        marcas = Arrays.stream(dadosConvertidosMarcas).map(m -> new MarcaVeiculo(m.codigo(), TipoVeiculo.CARRO, m.nome())).toList();
                        marcas.forEach(marca -> System.out.println("Cód: " + marca.getCodigo() + " Descrição: " + marca.getNome()));
                        for (MarcaVeiculo marca : marcas) {
                            if (!marcaRepository.existsByCodigoAndNome(marca.getCodigo(), marca.getNome())) {
                                marcaRepository.save(marca);
                            }
                        }
                    } else if (tipoAutomovel.contains("mot")) {
                        marcas = Arrays.stream(dadosConvertidosMarcas).map(m -> new MarcaVeiculo(m.codigo(), TipoVeiculo.MOTO, m.nome())).toList();
                        marcas.forEach(marca -> System.out.println("Cód: " + marca.getCodigo() + " Descrição: " + marca.getNome()));
                        for (MarcaVeiculo marca : marcas) {
                            if (!marcaRepository.existsByCodigoAndNome(marca.getCodigo(), marca.getNome())) {
                                marcaRepository.save(marca);
                            }
                        }
                    } else if (tipoAutomovel.contains("cam")) {
                        marcas = Arrays.stream(dadosConvertidosMarcas).map(m -> new MarcaVeiculo(m.codigo(), TipoVeiculo.CAMINHAO, m.nome())).toList();
                        marcas.forEach(marca -> System.out.println("Cód: " + marca.getCodigo() + " Descrição: " + marca.getNome()));
                        for (MarcaVeiculo marca : marcas) {
                            if (!marcaRepository.existsByCodigoAndNome(marca.getCodigo(), marca.getNome())) {
                                marcaRepository.save(marca);
                            }
                        }
                    } else {
                        System.out.println("ERRO! ");
                    }


                    System.out.println("Informe o nome ou o código da marca: ");
                    var entrada = leitura.nextLine().trim().toUpperCase();

                    Optional<DadosConvertidosMarcas> marcaEncotrada = Arrays.stream(dadosConvertidosMarcas)
                            .filter(m -> m.nome().toUpperCase().contains(entrada))
                            .findFirst();

                    MarcaVeiculo marcaSelecionada;

                    if (entrada.matches("\\d+")) {
                        marcaSelecionada = marcaRepository.findByCodigo(entrada).orElseThrow();
                        json = consumoApi.BuscaApi("https://parallelum.com.br/fipe/api/v1/" + tipoAutomovel + "/marcas/" + entrada + "/modelos");
                    } else if (marcaEncotrada.isPresent()) {
                        String codicoMarca = marcaEncotrada.get().codigo();
                        marcaSelecionada = marcaRepository
                                .findByCodigo(codicoMarca)
                                .orElseThrow();
                        json = consumoApi.BuscaApi("https://parallelum.com.br/fipe/api/v1/" + tipoAutomovel + "/marcas/" + codicoMarca + "/modelos");
                    } else {
                        marcaSelecionada = null;
                        System.out.println("Marca não encotrada! Digite os dados corretamente!");
                    }

                    System.out.println(json);
                    DadosConvertidosModelos dadosConvertidosModelos = converterDados.Conversor(json, DadosConvertidosModelos.class);


                    if (tipoAutomovel.contains("carr")) {
                        veiculos = dadosConvertidosModelos.modelos().stream()
                                .map(m -> {
                                    ModeloVeiculo modelo = new ModeloVeiculo(
                                            m.codigo(),
                                            m.nome(),
                                            TipoVeiculo.CARRO
                                    );
                                    modelo.setMarca(marcaSelecionada);

                                    return modelo;
                                })
                                .toList();
                        veiculos.forEach(mcarro -> System.out.println("Cód: " + mcarro.getCodigo() + " Descrição: " + mcarro.getModelo()));
                        for (ModeloVeiculo modeloVeiculo : veiculos) {
                            if (!modeloVeicularRepository.existsByCodigoAndTipo(modeloVeiculo.getCodigo(), modeloVeiculo.getTipo())) {
                                modeloVeicularRepository.save(modeloVeiculo);
                            }
                        }
                    } else if (tipoAutomovel.contains("mot")) {
                        veiculos = dadosConvertidosModelos.modelos().stream()
                                .map(m -> {
                                    ModeloVeiculo modelo = new ModeloVeiculo(
                                            m.codigo(),
                                            m.nome(),
                                            TipoVeiculo.MOTO
                                    );
                                    modelo.setMarca(marcaSelecionada);

                                    return modelo;
                                })
                                .toList();
                        veiculos.forEach(mmoto -> System.out.println("Cód: " + mmoto.getCodigo() + " Descrição: " + mmoto.getModelo()));
                        for (ModeloVeiculo modeloVeiculo : veiculos) {
                            if (!modeloVeicularRepository.existsByCodigoAndTipo(modeloVeiculo.getCodigo(), modeloVeiculo.getTipo())) {
                                modeloVeicularRepository.save(modeloVeiculo);
                            }
                        }
                    } else if (tipoAutomovel.contains("cam")) {
                        veiculos = dadosConvertidosModelos.modelos().stream()
                                .map(m -> {
                                    ModeloVeiculo modelo = new ModeloVeiculo(
                                            m.codigo(),
                                            m.nome(),
                                            TipoVeiculo.CAMINHAO
                                    );
                                    modelo.setMarca(marcaSelecionada);

                                    return modelo;
                                })
                                .toList();
                        veiculos.forEach(m -> System.out.println("Cód: " + m.getCodigo() + " Descrição: " + m.getModelo()));
                        for (ModeloVeiculo modeloVeiculo : veiculos) {
                            if (!modeloVeicularRepository.existsByCodigoAndTipo(modeloVeiculo.getCodigo(), modeloVeiculo.getTipo())) {
                                modeloVeicularRepository.save(modeloVeiculo);
                            }
                        }
                    } else {
                        System.out.println("ERRO! ");
                    }

                    System.out.println("Digite um trecho do nome do modelo que deseja visualizar: ");
                    var trechoDoModelo = leitura.nextLine().trim().toUpperCase();
                    dadosConvertidosModelos.modelos().stream()
                            .filter(m -> m.nome().toUpperCase().contains(trechoDoModelo))
                            .forEach(m -> System.out.println("Cód: " + m.codigo() + " Descrição: " + m.nome()));
                } catch (ExceptionApi | ExceptionConversao e) {
                    System.out.println(e.getMessage());
                }
            } else if (tipoDeConsulta == 2) {
                System.out.println("""
                        Como desejar fazer a busca?
                        1 - Buscar por tipo
                        2 - Busca por marca""");

                var buscaDados = leitura.nextInt();
                leitura.nextLine();

                switch (buscaDados) {
                    case 1:
                        buscaPorTipo();
                        break;
                    case 2:
                        buscaPorMarca();
                        break;
                }

//
//            List<MarcaVeiculo> veiculos = marcaRepository.findAll();
//            veiculos.forEach(e -> System.out.println("Marca: " + e.getNome() + " - Modelos: " + e.getModelos()));
            } else if (tipoDeConsulta == 0) {
                inicializar = 0;
                System.out.println("Sistema finalizado!");
            } else {
                System.out.println("Consulta inválida!");
            }
        }
    }

    private void buscaPorTipo () {
        System.out.println("Que veiculo deseja consultar? ");
        var marcaVeiculo = leitura.nextLine();
        TipoVeiculo tipo = TipoVeiculo.fromString(marcaVeiculo);
        List<MarcaVeiculo> marcaPorTipo = marcaRepository.findByTipoVeiculo(tipo);
        System.out.println(marcaPorTipo + " encotrados");
        marcaPorTipo.forEach(System.out::println);
    }

    public void buscaPorMarca () {
        System.out.println("Digite a marca do veiculo que deseja consultar: ");
        var consulta = leitura.nextLine();
        List<MarcaVeiculo> veiculoConsutado = marcaRepository.findByNomeContainingIgnoreCase(consulta);
        if (!veiculoConsutado.isEmpty()) {
            veiculoConsutado.forEach(System.out::println);
        } else {
            System.out.println("Veiculo não encotrado!");
        }
    }
}
