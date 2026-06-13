package com.jhonatan.tabelafipe.repository;

import com.jhonatan.tabelafipe.model.MarcaVeiculo;
import com.jhonatan.tabelafipe.model.TipoVeiculo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MarcaVeiculoRepository extends JpaRepository<MarcaVeiculo, Long> {
    boolean existsByCodigoAndNome(String codigo, String nome);
    Optional<MarcaVeiculo> findByCodigo(String codigo);
    List<MarcaVeiculo> findByNomeContainingIgnoreCase(String consulta);
    List<MarcaVeiculo> findByTipoVeiculo(TipoVeiculo tipoVeiculo);
}
