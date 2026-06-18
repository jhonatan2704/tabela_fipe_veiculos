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

    public MarcaController(MarcaVeiculoRepository marcaVeiculo) {
        this.marcaVeiculo = marcaVeiculo;
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


    @GetMapping("/sincronizar")
    public String rodarSincronizacao() {
        FipeSyncService fipeSyncService = new FipeSyncService();
        fipeSyncService.setRepository(marcaVeiculo);
        ConsumoApi consumoApi = new ConsumoApi();
        String dados = consumoApi.BuscaApi("https://parallelum.com.br/fipe/api/v1/marcas");
        ConverterDados converterDados = new ConverterDados();
        DadosConvertidosMarcas[] dadosConvertidosMarcas = converterDados.Conversor(dados, DadosConvertidosMarcas[].class);
        fipeSyncService.sincronizarDados(Arrays.asList(dadosConvertidosMarcas));
        return "Sincronização concluída!";
    }

    @GetMapping("/testar-banco")
    public String testarBanco() {
        if (marcaVeiculo == null) {
            return "Erro: Repository é nulo!";
        }
        return "Sucesso: Repository carregado, total de marcas: " + marcaVeiculo.count();
    }
}
