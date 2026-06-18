package com.jhonatan.tabelafipe.service;

import com.jhonatan.tabelafipe.model.DadosConvertidosMarcas;
import com.jhonatan.tabelafipe.model.MarcaVeiculo;
import com.jhonatan.tabelafipe.repository.MarcaVeiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class FipeSyncService {

    @Autowired
    private MarcaVeiculoRepository repository;

    public void setRepository(MarcaVeiculoRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public void sincronizarDados(List<DadosConvertidosMarcas> listaApi) {
        for (DadosConvertidosMarcas dto : listaApi) {
            // Verifica se já existe para não duplicar
            if (!repository.existsByNome(dto.nome())) {
                // Cria a entidade do seu banco a partir do que veio da API
                MarcaVeiculo novaMarca = new MarcaVeiculo();
                novaMarca.setNome(dto.nome());
                novaMarca.setCodigo(dto.codigo());

                repository.save(novaMarca);
            }
        }
    }
}