package com.genomebank.repositories;

import com.genomebank.entities.Genome;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

/**
 * Repositorio para la entidad Genome.
 * Permite operaciones CRUD y filtrado por especie.
 */
public interface GenomeRepository extends JpaRepository<Genome, Long> {

    /** Listar todos los genomas de una especie */
    List<Genome> findBySpeciesId(Long speciesId);
}
