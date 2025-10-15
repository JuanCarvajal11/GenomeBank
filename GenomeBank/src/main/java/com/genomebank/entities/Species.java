package com.genomebank.entities;

import jakarta.persistence.*;
import lombok.Data;
import java.util.HashSet;
import java.util.Set;

/**
 * Representa una especie biológica.
 * Cada especie puede tener múltiples genomas asociados.
 */
@Data
@Entity
@Table(name = "species")
public class Species {

    /** Identificador único autogenerado */
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Nombre científico único y obligatorio */
    @Column(name = "scientific_name", nullable = false, unique = true)
    private String scientificName;

    /** Nombre común opcional */
    @Column(name = "common_name")
    private String commonName;

    /** Genomas asociados a esta especie */
    @OneToMany(mappedBy = "species")
    private Set<Genome> genomes = new HashSet<>();
}
