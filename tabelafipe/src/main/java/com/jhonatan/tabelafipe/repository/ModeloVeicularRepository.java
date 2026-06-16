package com.jhonatan.tabelafipe.repository;

import com.jhonatan.tabelafipe.model.ModeloVeiculo;
import com.jhonatan.tabelafipe.model.TipoVeiculo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ModeloVeicularRepository extends JpaRepository<ModeloVeiculo, Long> {
    boolean existsByCodigoAndTipo(String codigo, TipoVeiculo tipo);

    List<ModeloVeiculo> findByTipo(TipoVeiculo tipo);
    List<ModeloVeiculo> findByModeloContainingIgnoreCase(String modelo);
}
