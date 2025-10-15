package com.genomebank.repositories;

import com.genomebank.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

/**
 * Repositorio para la entidad User.
 * Permite operaciones CRUD y consultas adicionales por username o email.
 */
public interface UserRepository extends JpaRepository<User, Long> {

    /** Buscar un usuario por su nombre de usuario */
    Optional<User> findByUsername(String username);

    /** Buscar un usuario por su correo electrónico */
    Optional<User> findByEmail(String email);
}
