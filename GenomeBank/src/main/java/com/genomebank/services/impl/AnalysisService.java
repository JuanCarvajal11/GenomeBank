package com.genomebank.services.impl;

import com.genomebank.dtos.ChromosomeStatsOutDTO;
import com.genomebank.entities.Chromosome;
import com.genomebank.entities.Gene;
import com.genomebank.repositories.ChromosomeRepository;
import com.genomebank.repositories.GeneRepository;
import com.genomebank.services.IAnalysisService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Implementación del servicio de análisis genómico.
 */
@Service
public class AnalysisService implements IAnalysisService {

    private final GeneRepository geneRepository;
    private final ChromosomeRepository chromosomeRepository;

    public AnalysisService(GeneRepository geneRepository, ChromosomeRepository chromosomeRepository) {
        this.geneRepository = geneRepository;
        this.chromosomeRepository = chromosomeRepository;
    }

    @Override
    public List<Gene> obtenerGenesPorRango(Long chromosomeId, Long start, Long end) {
        if (chromosomeId == null || start == null || end == null || start < 0 || end < start) {
            throw new IllegalArgumentException("Parámetros de rango inválidos.");
        }

        return geneRepository
                .findByChromosomeIdAndStartPositionGreaterThanEqualAndEndPositionLessThanEqual(
                        chromosomeId, start, end);
    }

    @Override
    public Optional<ChromosomeStatsOutDTO> calcularEstadisticasCromosoma(Long chromosomeId) {
        try {
            Chromosome chromosome = chromosomeRepository.findById(chromosomeId)
                    .orElseThrow(() -> new RuntimeException("Cromosoma no encontrado."));

            String sequence = chromosome.getSequence();
            if (sequence == null || sequence.isEmpty()) {
                return Optional.empty();
            }

            long totalLength = sequence.length();
            long gcCount = sequence.chars()
                    .filter(c -> c == 'G' || c == 'C' || c == 'g' || c == 'c')
                    .count();

            double gcPercentage = (double) gcCount / totalLength * 100;

            int geneCount = geneRepository.findByChromosomeId(chromosomeId).size();

            ChromosomeStatsOutDTO stats =
                    new ChromosomeStatsOutDTO(chromosomeId, totalLength, gcPercentage, geneCount);

            return Optional.of(stats);

        } catch (Exception e) {
            throw new RuntimeException("Error al calcular estadísticas: " + e.getMessage());
        }
    }
}
