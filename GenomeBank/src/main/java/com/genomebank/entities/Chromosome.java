package com.genomebank.entities;

import jakarta.persistence.*;
import lombok.Data;
import java.util.HashSet;
import java.util.Set;

/**
 * Representa un cromosoma dentro de un genoma.
 * Cada cromosoma puede tener múltiples genes.
 */
@Data
@Entity
@Table(name = "chromosomes")
public class Chromosome {

    /** Identificador único autogenerado */
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Nombre del cromosoma (ej. X, 2L) */
    @Column(nullable = false)
    private String name;

    /** Longitud en pares de bases */
    @Column(nullable = false)
    private Long length;

    /** Secuencia completa de ADN */
    @Lob
    @Column(nullable = false)
    private String sequence;

    /** Genoma al que pertenece */
    @ManyToOne
    @JoinColumn(name = "genome_id", nullable = false)
    private Genome genome;

    /** Genes contenidos en el cromosoma */
    @OneToMany(mappedBy = "chromosome")
    private Set<Gene> genes = new HashSet<>();
}
