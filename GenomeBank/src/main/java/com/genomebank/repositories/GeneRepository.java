package com.genomebank.repositories;

import com.genomebank.entities.Gene;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

/**
 * Repositorio para la entidad Gene.
 * Permite operaciones CRUD y consultas filtradas.
 */
public interface GeneRepository extends JpaRepository<Gene, Long> {

    /** Listar genes de un cromosoma específico */
    List<Gene> findByChromosomeId(Long chromosomeId);

    /** Filtrar genes por símbolo */
    List<Gene> findBySymbolContainingIgnoreCase(String symbol);

    /** Filtrar genes por rango dentro del cromosoma */
    List<Gene> findByChromosomeIdAndStartPositionGreaterThanEqualAndEndPositionLessThanEqual(
            Long chromosomeId, Long start, Long end);

    /** Contar cuántos genes tiene un cromosoma */
    int countByChromosomeId(Long chromosomeId);
}
