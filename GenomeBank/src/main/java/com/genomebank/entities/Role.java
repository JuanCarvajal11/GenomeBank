package com.genomebank.entities;

import jakarta.persistence.*;
import lombok.Data;

/**
 * Representa un rol dentro del sistema.
 * Define permisos como ADMIN o USER.
 */
@Data
@Entity
@Table(name = "roles")
public class Role {

    /** Identificador único autogenerado */
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Nombre del rol (único) */
    @Column(nullable = false, unique = true, length = 50)
    private String name;
}
