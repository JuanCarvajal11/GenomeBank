package com.genomebank.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO de salida para las estadísticas de análisis de cromosomas.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChromosomeStatsOutDTO {

    private Long chromosomeId;
    private long sequenceLength;
    private double gcPercentage;
    private int geneCount;
}
