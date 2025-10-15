package com.genomebank.entities;

import jakarta.persistence.*;
import lombok.Data;

/**
 * Relación muchos a muchos entre Gene y Function.
 * Contiene evidencia opcional del vínculo.
 */
@Data
@Entity
@Table(name = "gene_functions", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"gene_id", "function_id"})
})
public class GeneFunction {

    /** Identificador único autogenerado */
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Gen asociado */
    @ManyToOne
    @JoinColumn(name = "gene_id", nullable = false)
    private Gene gene;

    /** Función asociada */
    @ManyToOne
    @JoinColumn(name = "function_id", nullable = false)
    private Function function;

    /** Evidencia del vínculo (experimental, computacional, predicha) */
    @Column
    private String evidence;
}
