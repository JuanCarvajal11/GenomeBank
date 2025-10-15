package com.genomebank.entities;

import jakarta.persistence.*;
import lombok.Data;
import java.util.HashSet;
import java.util.Set;

/**
 * Representa un genoma de una especie.
 * Cada genoma puede contener múltiples cromosomas.
 */
@Data
@Entity
@Table(name = "genomes")
public class Genome {

    /** Identificador único autogenerado */
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Nombre de la versión o ensamblaje del genoma */
    @Column(name = "version_name", nullable = false)
    private String versionName;

    /** Especie a la que pertenece el genoma */
    @ManyToOne
    @JoinColumn(name = "species_id", nullable = false)
    private Species species;

    /** Cromosomas asociados a este genoma */
    @OneToMany(mappedBy = "genome")
    private Set<Chromosome> chromosomes = new HashSet<>();
}
