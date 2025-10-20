package com.genomebank.services;

import com.genomebank.dtos.ChromosomeStatsOutDTO;
import com.genomebank.entities.Gene;

import java.util.List;
import java.util.Optional;

/**
 * Interfaz del servicio de análisis genómico.
 */
public interface IAnalysisService {

    /**
     * Devuelve los genes ubicados dentro de un rango específico de un cromosoma.
     */
    List<Gene> obtenerGenesPorRango(Long chromosomeId, Long start, Long end);

    /**
     * Calcula estadísticas básicas de composición de un cromosoma.
     */
    Optional<ChromosomeStatsOutDTO> calcularEstadisticasCromosoma(Long chromosomeId);
}
