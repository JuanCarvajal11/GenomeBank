package com.genomebank.repositories;

import com.genomebank.entities.GeneFunction;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

/**
 * Repositorio para la tabla intermedia GeneFunction.
 * Permite operaciones CRUD y consultas por gen, función o evidencia.
 */
public interface GeneFunctionRepository extends JpaRepository<GeneFunction, Long> {

    /** Listar relaciones por gen */
    List<GeneFunction> findByGeneId(Long geneId);

    /** Listar relaciones por función */
    List<GeneFunction> findByFunctionId(Long functionId);

    /** Filtrar relaciones por evidencia (experimental, computacional, predicha) */
    List<GeneFunction> findByEvidence(String evidence);

    /** Eliminar relación específica entre gen y función */
    void deleteByGeneIdAndFunctionId(Long geneId, Long functionId);
}
