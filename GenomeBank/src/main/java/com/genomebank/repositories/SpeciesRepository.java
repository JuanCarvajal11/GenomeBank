package com.genomebank.repositories;

import com.genomebank.entities.Species;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

/**
 * Repositorio para la entidad Species.
 * Permite CRUD y búsqueda por nombre científico.
 */
public interface SpeciesRepository extends JpaRepository<Species, Long> {

    /** Buscar una especie por su nombre científico */
    Optional<Species> findByScientificName(String scientificName);
}
