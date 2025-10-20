package com.genomebank.controllers;

import com.genomebank.dtos.ChromosomeInDTO;
import com.genomebank.dtos.ChromosomeOutDTO;
import com.genomebank.services.IChromosomeService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chromosomes")
public class ChromosomeController {

    private final IChromosomeService chromosomeService;

    public ChromosomeController(IChromosomeService chromosomeService) {
        this.chromosomeService = chromosomeService;
    }

    /**
     * GET /chromosomes → Listar todos los cromosomas o filtrar por genoma (?genomeId=).
     * Acceso: ADMIN y USER
     * @param genomeId ID del genoma para filtrar (opcional).
     * @return ResponseEntity con la lista de cromosomas.
     */
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping
    public ResponseEntity<List<ChromosomeOutDTO>> obtenerChromosomes(
            @RequestParam(required = false) Long genomeId) {

        if (genomeId != null) {
            return ResponseEntity.ok(chromosomeService.obtenerCromosomasPorGenoma(genomeId));
        }
        return ResponseEntity.ok(chromosomeService.obtenerCromosomas());
    }

    /**
     * GET /chromosomes/{id} → Consultar un cromosoma específico.
     * Acceso: ADMIN y USER
     * @param id ID del cromosoma.
     * @return ResponseEntity con el cromosoma o 404 si no existe.
     */
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("/{id}")
    public ResponseEntity<ChromosomeOutDTO> obtenerPorId(@PathVariable Long id) {
        return chromosomeService.obtenerCromosomaPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * POST /chromosomes → Crear un nuevo cromosoma (solo ADMIN).
     * Acceso: Solo ADMIN
     * @param chromosomeInDTO Objeto ChromosomeInDTO a crear.
     * @return ResponseEntity con el cromosoma creado.
     */
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<ChromosomeOutDTO> crearChromosome(@RequestBody ChromosomeInDTO chromosomeInDTO) {
        return ResponseEntity.ok(chromosomeService.crearCromosoma(chromosomeInDTO));
    }

    /**
     * PUT /chromosomes/{id} → Actualizar un cromosoma (solo ADMIN).
     * Acceso: Solo ADMIN
     * @param id ID del cromosoma a actualizar.
     * @param chromosomeInDTO ChromosomeInDTO con datos actualizados.
     * @return ResponseEntity con el cromosoma actualizado o 404 si no existe.
     */
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<ChromosomeOutDTO> actualizarChromosome(
            @PathVariable Long id,
            @RequestBody ChromosomeInDTO chromosomeInDTO) {

        return chromosomeService.actualizarCromosoma(id, chromosomeInDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * DELETE /chromosomes/{id} → Eliminar un cromosoma (solo ADMIN).
     * Acceso: Solo ADMIN
     * @param id ID del cromosoma a eliminar.
     * @return ResponseEntity con el cromosoma eliminado o 404 si no existe.
     */
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<ChromosomeOutDTO> eliminarChromosome(@PathVariable Long id) {
        return chromosomeService.eliminarCromosoma(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}