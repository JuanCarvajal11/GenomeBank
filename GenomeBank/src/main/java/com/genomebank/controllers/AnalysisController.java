package com.genomebank.controllers;

import com.genomebank.dtos.ChromosomeStatsOutDTO;
import com.genomebank.entities.Gene;
import com.genomebank.services.IAnalysisService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para los análisis de cromosomas y genes.
 */
@RestController
@RequestMapping("/analysis")
public class AnalysisController {

    private final IAnalysisService analysisService;

    public AnalysisController(IAnalysisService analysisService) {
        this.analysisService = analysisService;
    }

    /**
     * GET /analysis/genes?chromosomeId=&start=&end=
     * Devuelve los genes ubicados dentro de un rango específico del cromosoma.
     * Acceso: ADMIN y USER
     */
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("/genes")
    public ResponseEntity<List<Gene>> obtenerGenesPorRango(
            @RequestParam Long chromosomeId,
            @RequestParam Long start,
            @RequestParam Long end) {
        return ResponseEntity.ok(analysisService.obtenerGenesPorRango(chromosomeId, start, end));
    }

    /**
     * GET /analysis/sequence/stats?chromosomeId=
     * Calcula estadísticas básicas de composición (GC%, longitud, número de genes).
     * Acceso: ADMIN y USER
     */
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("/sequence/stats")
    public ResponseEntity<ChromosomeStatsOutDTO> obtenerEstadisticas(
            @RequestParam Long chromosomeId) {
        return analysisService.calcularEstadisticasCromosoma(chromosomeId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
