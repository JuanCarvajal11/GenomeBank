package com.genomebank.repositories;

import com.genomebank.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

/**
 * Repositorio para la entidad Role.
 * Permite CRUD básico y búsqueda por nombre de rol.
 */
public interface RoleRepository extends JpaRepository<Role, Long> {

    /** Buscar un rol por su nombre */
    Optional<Role> findByName(String name);
}
