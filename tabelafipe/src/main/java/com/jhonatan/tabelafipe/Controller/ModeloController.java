package com.jhonatan.tabelafipe.Controller;

import com.jhonatan.tabelafipe.model.ModeloVeiculo;
import com.jhonatan.tabelafipe.model.TipoVeiculo;
import com.jhonatan.tabelafipe.repository.ModeloVeicularRepository;
import com.jhonatan.tabelafipe.service.VeiculoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ModeloController {
    private final ModeloVeicularRepository modeloVeiculo;
    private final VeiculoService veiculoService;

    public ModeloController(ModeloVeicularRepository modeloVeiculo, VeiculoService veiculoService) {
        this.modeloVeiculo = modeloVeiculo;
        this.veiculoService = veiculoService;
    }

    @GetMapping("/modelos")
    public List<ModeloVeiculo> listarModelos() {
        return veiculoService.exibirModelos();
    }

    @GetMapping("/modelos/{modelo}")
    public List<ModeloVeiculo> buscarPorModelo(
            @PathVariable String modelo) {

        return veiculoService.exibirModelosPorNome(modelo);
    }

    @GetMapping("/modelos/tipo/{tipo}")
    public List<ModeloVeiculo> buscarPorTipo(
            @PathVariable TipoVeiculo tipo) {
        return veiculoService.exibirModelosPorTipo(tipo);
    }

    @PostMapping("/adicionarModelo")
    public List<ModeloVeiculo> adicionarModelo(TipoVeiculo tipoVeiculo, String nome) {
        veiculoService.adicionarModelo(tipoVeiculo, nome);
        return listarModelos();
    }

}
