package com.genomebank.controllers;

import com.genomebank.dtos.GeneInDTO;
import com.genomebank.dtos.GeneOutDTO;
import com.genomebank.services.IGeneService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/genes")
public class GeneController {

    private final IGeneService geneService;

    public GeneController(IGeneService geneService) {
        this.geneService = geneService;
    }

    /**
     * GET /genes → Listar todos los genes con filtros opcionales
     * Parámetros: ?chromosomeId=, ?start=, ?end=, ?symbol=
     */
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping
    public ResponseEntity<List<GeneOutDTO>> obtenerGenes(
            @RequestParam(required = false) Long chromosomeId,
            @RequestParam(required = false) Long start,
            @RequestParam(required = false) Long end,
            @RequestParam(required = false) String symbol) {

        List<GeneOutDTO> genes = geneService.obtenerGenes(chromosomeId, start, end, symbol);
        return ResponseEntity.ok(genes);
    }

    /**
     * GET /genes/{id} → Consultar un gen específico
     */
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("/{id}")
    public ResponseEntity<GeneOutDTO> obtenerGenPorId(@PathVariable Long id) {
        return geneService.obtenerGenPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * POST /genes → Registrar un nuevo gen (solo ADMIN)
     */
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<GeneOutDTO> crearGen(@RequestBody GeneInDTO geneInDTO) {
        GeneOutDTO nuevoGen = geneService.crearGen(geneInDTO);
        return ResponseEntity.ok(nuevoGen);
    }

    /**
     * PUT /genes/{id} → Actualizar un gen (solo ADMIN)
     */
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<GeneOutDTO> actualizarGen(
            @PathVariable Long id,
            @RequestBody GeneInDTO geneInDTO) {

        return geneService.actualizarGen(id, geneInDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * DELETE /genes/{id} → Eliminar un gen (solo ADMIN)
     */
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarGen(@PathVariable Long id) {
        geneService.eliminarGen(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * GET /genes/{id}/sequence → Consultar la secuencia del gen
     */
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("/{id}/sequence")
    public ResponseEntity<String> obtenerSecuenciaGen(@PathVariable Long id) {
        return geneService.obtenerSecuenciaGen(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * PUT /genes/{id}/sequence → Registrar o actualizar la secuencia de ADN del gen (ADMIN)
     */
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}/sequence")
    public ResponseEntity<Void> actualizarSecuenciaGen(
            @PathVariable Long id,
            @RequestBody String sequence) {

        boolean actualizado = geneService.actualizarSecuenciaGen(id, sequence);
        return actualizado ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }
}