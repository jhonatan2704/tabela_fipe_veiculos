package com.jhonatan.tabelafipe.service;

import com.jhonatan.tabelafipe.model.DadosConvertidosMarcas;
import com.jhonatan.tabelafipe.model.MarcaVeiculo;
import com.jhonatan.tabelafipe.model.ModeloVeiculo;
import com.jhonatan.tabelafipe.model.TipoVeiculo;
import com.jhonatan.tabelafipe.repository.MarcaVeiculoRepository;
import com.jhonatan.tabelafipe.repository.ModeloVeicularRepository;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class VeiculoService {
    private final MarcaVeiculoRepository marcaVeiculoRepository;
    private final ModeloVeicularRepository modeloVeiculoRepository;
    ConsumoApi  consumoApi;
    MarcaVeiculo marca = new MarcaVeiculo();
    private List<DadosConvertidosMarcas[]> marcaVeiculos;

    public VeiculoService(MarcaVeiculoRepository marcaVeiculoRepository, ModeloVeicularRepository modeloVeiculoRepository) {
        this.marcaVeiculoRepository = marcaVeiculoRepository;
        this.modeloVeiculoRepository = modeloVeiculoRepository;
        this.consumoApi = new ConsumoApi();
    }

    public void BuscaMarcaVeiculo(TipoVeiculo tipoVeiculo, String marcaVeiculo) {
        var json = consumoApi.BuscaApi("https://parallelum.com.br/fipe/api/v1/" + tipoVeiculo + "/" + marcaVeiculo);
        ConverterDados converterDados = new ConverterDados();
        MarcaVeiculo[] marcaVeiculos = converterDados.Conversor(json, MarcaVeiculo[].class);

        for (MarcaVeiculo dto : marcaVeiculos) {
            marca.setNome(dto.getNome());
            marca.setCodigo(dto.getCodigo());
            marca.setTipoVeiculo(tipoVeiculo);

            marcaVeiculoRepository.save(marca);
        }
    }

    public List<MarcaVeiculo> exibirMarcas() {
        return marcaVeiculoRepository.findAll();
    }

    public List<MarcaVeiculo> exibirMarcasPorNome(String nome) {
        return marcaVeiculoRepository.findByNomeContainingIgnoreCase(nome);
    }

    public List<ModeloVeiculo> exibirModelos() {
        return modeloVeiculoRepository.findAll();
    }

    public List<ModeloVeiculo> exibirModelosPorNome(String nome) {
        return modeloVeiculoRepository.findByModeloContainingIgnoreCase(nome);
    }

    public List<ModeloVeiculo> exibirModelosPorTipo(TipoVeiculo tipo) {
        return modeloVeiculoRepository.findByTipo(tipo);
    }
}
