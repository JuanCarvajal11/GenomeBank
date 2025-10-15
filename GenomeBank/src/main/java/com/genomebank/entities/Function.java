package com.genomebank.entities;

import jakarta.persistence.*;
import lombok.Data;
import java.util.HashSet;
import java.util.Set;

/**
 * Representa una función biológica.
 * Cada función puede estar asociada a múltiples genes.
 */
@Data
@Entity
@Table(name = "functions")
public class Function {

    /** Identificador único autogenerado */
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Código único de la función (ej. GO:0003700) */
    @Column(nullable = false, unique = true)
    private String code;

    /** Nombre descriptivo de la función */
    @Column(nullable = false)
    private String name;

    /** Categoría (BP, MF, CC) */
    @Column
    private String category;

    /** Genes asociados a esta función */
    @OneToMany(mappedBy = "function")
    private Set<GeneFunction> geneFunctions = new HashSet<>();
}
