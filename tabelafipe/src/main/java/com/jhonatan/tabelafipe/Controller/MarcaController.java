package com.jhonatan.tabelafipe.Controller;

import com.jhonatan.tabelafipe.model.MarcaVeiculo;
import com.jhonatan.tabelafipe.repository.MarcaVeiculoRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
