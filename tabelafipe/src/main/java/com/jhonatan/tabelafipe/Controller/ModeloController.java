package com.jhonatan.tabelafipe.Controller;

import com.jhonatan.tabelafipe.model.ModeloVeiculo;
import com.jhonatan.tabelafipe.model.TipoVeiculo;
import com.jhonatan.tabelafipe.repository.ModeloVeicularRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ModeloController {
    private final ModeloVeicularRepository modeloVeiculo;

    public ModeloController(ModeloVeicularRepository modeloVeiculo) {
        this.modeloVeiculo = modeloVeiculo;
    }

    @GetMapping("/modelos")
    public List<ModeloVeiculo> listarModelos() {
        return modeloVeiculo.findAll();
    }

    @GetMapping("/modelos/{modelo}")
    public List<ModeloVeiculo> buscarPorModelo(
            @PathVariable String modelo) {

        return modeloVeiculo.findByModeloContainingIgnoreCase(modelo);
    }

    @GetMapping("/modelos/tipo/{tipo}")
    public List<ModeloVeiculo> buscarPorTipo(
            @PathVariable TipoVeiculo tipo) {

        return modeloVeiculo.findByTipo(tipo);
    }

}
