package com.genomebank.entities;

import jakarta.persistence.*;
import lombok.Data;
import java.util.HashSet;
import java.util.Set;

/**
 * Representa un gen dentro de un cromosoma.
 * Puede estar asociado a múltiples funciones biológicas.
 */
@Data
@Entity
@Table(name = "genes")
public class Gene {

    /** Identificador único autogenerado */
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Símbolo del gen (ej. BRCA1) */
    @Column(nullable = false)
    private String symbol;

    /** Posición inicial del gen en el cromosoma */
    @Column(name = "start_position", nullable = false)
    private Long startPosition;

    /** Posición final del gen en el cromosoma */
    @Column(name = "end_position", nullable = false)
    private Long endPosition;

    /** Hebra del gen ('+' o '-') */
    @Column(length = 1)
    private Character strand;

    /** Secuencia de ADN del gen */
    @Lob
    @Column(nullable = false)
    private String sequence;

    /** Cromosoma al que pertenece el gen */
    @ManyToOne
    @JoinColumn(name = "chromosome_id", nullable = false)
    private Chromosome chromosome;

    /** Relación con funciones biológicas */
    @OneToMany(mappedBy = "gene")
    private Set<GeneFunction> geneFunctions = new HashSet<>();
}
