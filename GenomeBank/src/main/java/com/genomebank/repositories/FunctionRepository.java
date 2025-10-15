package com.genomebank.repositories;

import com.genomebank.entities.Function;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

/**
 * Repositorio para la entidad Function.
 * Permite operaciones CRUD y filtrado por código o categoría.
 */
public interface FunctionRepository extends JpaRepository<Function, Long> {

    /** Buscar una función por su código */
    Optional<Function> findByCode(String code);

    /** Listar funciones por categoría (BP, MF, CC) */
    List<Function> findByCategory(String category);
}
