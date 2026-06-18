package com.jhonatan.tabelafipe.Controller;

import com.jhonatan.tabelafipe.model.DadosConvertidosMarcas;
import com.jhonatan.tabelafipe.model.MarcaVeiculo;
import com.jhonatan.tabelafipe.model.ModeloVeiculo;
import com.jhonatan.tabelafipe.model.TipoVeiculo;
import com.jhonatan.tabelafipe.repository.MarcaVeiculoRepository;
import com.jhonatan.tabelafipe.service.ConsumoApi;
import com.jhonatan.tabelafipe.service.ConverterDados;
import com.jhonatan.tabelafipe.service.FipeSyncService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
public class MarcaController {
    private final MarcaVeiculoRepository marcaVeiculo;
    private final FipeSyncService fipeSyncService;
    private final ConsumoApi consumoApi;
    private final ConverterDados converterDados;

    public MarcaController(MarcaVeiculoRepository marcaVeiculo, FipeSyncService fipeSyncService, ConsumoApi consumoApi, ConverterDados converterDados) {
        this.marcaVeiculo = marcaVeiculo;
        this.fipeSyncService = fipeSyncService;
        this.consumoApi = consumoApi;
        this.converterDados = converterDados;
    }

    @GetMapping("/marcas")
    public List<MarcaVeiculo> listarMarcas() {
        return marcaVeiculo.findAll();
    }

    @GetMapping("/marcas/{marca}")
    public List<MarcaVeiculo> buscarPorMarca(
            @PathVariable String marca) {

        return marcaVeiculo.findByNomeContainingIgnoreCase(marca);
    }


    @GetMapping("/marcas/{id}/modelos")
    public Optional<MarcaVeiculo>  buscarPorIdModelo(@PathVariable Long id) {
        return marcaVeiculo.findById(id);
    }


    @GetMapping("/sincronizar/{tipo}")
    public String rodarSincronizacao(@PathVariable String tipo) {
        try {
            TipoVeiculo tipoVeiculo = TipoVeiculo.fromString(tipo);
            String url = "https://parallelum.com.br/fipe/api/v1/" + tipo + "/marcas";
            String json = consumoApi.BuscaApi(url);
            DadosConvertidosMarcas[] listaMarcas = converterDados.Conversor(json, DadosConvertidosMarcas[].class);
            fipeSyncService.sincronizarDados(Arrays.asList(listaMarcas), tipoVeiculo);

            return "Sincronização de " + tipo + " concluída com sucesso!";

        } catch (IllegalArgumentException e) {
            return "Erro: Tipo inválido! Use 'carros', 'motos' ou 'caminhoes'.";
        } catch (Exception e) {
            return "Erro crítico durante a sincronização: " + e.getMessage();
        }
    }
}
