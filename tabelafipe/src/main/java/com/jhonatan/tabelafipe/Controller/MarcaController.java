package com.jhonatan.tabelafipe.Controller;

import com.jhonatan.tabelafipe.model.DadosConvertidosMarcas;
import com.jhonatan.tabelafipe.model.MarcaVeiculo;
import com.jhonatan.tabelafipe.model.ModeloVeiculo;
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

    public MarcaController(MarcaVeiculoRepository marcaVeiculo, FipeSyncService fipeSyncService) {
        this.marcaVeiculo = marcaVeiculo;
        this.fipeSyncService = fipeSyncService;
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
            String url = "https://parallelum.com.br/fipe/api/v1/" + tipo + "/marcas";

            // Etapa 1: Consumo
            ConsumoApi consumoApi = new ConsumoApi();
            String dados = consumoApi.BuscaApi(url);
            if (dados == null || dados.contains("error")) {
                return "Falha na etapa 1 (API): A URL " + url + " retornou erro ou nulo.";
            }

            // Etapa 2: Conversão
            ConverterDados converterDados = new ConverterDados();
            DadosConvertidosMarcas[] dadosConvertidos = converterDados.Conversor(dados, DadosConvertidosMarcas[].class);
            if (dadosConvertidos == null || dadosConvertidos.length == 0) {
                return "Falha na etapa 2 (Conversão): O JSON retornado não foi convertido corretamente.";
            }

            // Etapa 3: Banco
            fipeSyncService.sincronizarDados(Arrays.asList(dadosConvertidos));

            return "Sincronização de " + tipo + " concluída com sucesso! Total processado: " + dadosConvertidos.length;

        } catch (Exception e) {
            return "Erro Crítico na Etapa 3 (Banco/Service): " + e.getMessage();
        }
    }
}
