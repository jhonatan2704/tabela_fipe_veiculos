package com.jhonatan.tabelafipe.Controller;

import com.jhonatan.tabelafipe.model.MarcaVeiculo;
import com.jhonatan.tabelafipe.model.TipoVeiculo;
import com.jhonatan.tabelafipe.repository.MarcaVeiculoRepository;
import com.jhonatan.tabelafipe.service.VeiculoService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class MarcaController {
    private final MarcaVeiculoRepository marcaVeiculo;
    private final VeiculoService veiculoService;


    public MarcaController(MarcaVeiculoRepository marcaVeiculo, VeiculoService veiculoService) {
        this.marcaVeiculo = marcaVeiculo;
        this.veiculoService = veiculoService;
    }

    @GetMapping("/marcas")
    public List<MarcaVeiculo> listarMarcas() {
        return veiculoService.exibirMarcas();
    }

    @GetMapping("/marcas/{marca}")
    public List<MarcaVeiculo> buscarPorMarca(
            @PathVariable String marca) {

        return veiculoService.exibirMarcasPorNome(marca);
    }

    @PostMapping(value = "/adicionar")
    @ResponseStatus(HttpStatus.CREATED)
    public String adicionarMarca(@RequestBody MarcaVeiculo request) {
        veiculoService.BuscaMarcaVeiculo(request.getTipoVeiculo(), request.getNome());
        return "Marcas processadas com sucesso!";
    }

}
