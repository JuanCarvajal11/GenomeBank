package com.genomebank.repositories;

import com.genomebank.entities.Chromosome;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

/**
 * Repositorio para la entidad Chromosome.
 * Permite operaciones CRUD y filtrado por genoma.
 */
public interface ChromosomeRepository extends JpaRepository<Chromosome, Long> {

    /** Listar todos los cromosomas de un genoma específico */
    List<Chromosome> findByGenomeId(Long genomeId);
}
