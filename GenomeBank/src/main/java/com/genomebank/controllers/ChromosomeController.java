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
     */
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<ChromosomeOutDTO> crearChromosome(@RequestBody ChromosomeInDTO chromosomeInDTO) {
        return ResponseEntity.ok(chromosomeService.crearCromosoma(chromosomeInDTO));
    }

    /**
     * PUT /chromosomes/{id} → Actualizar un cromosoma (solo ADMIN).
     * Acceso: Solo ADMIN
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
     */
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<ChromosomeOutDTO> eliminarChromosome(@PathVariable Long id) {
        return chromosomeService.eliminarCromosoma(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    //Gestión de secuencias de cromosomas

    /**
     * GET /chromosomes/{id}/sequence → Consultar la secuencia completa de ADN.
     * Acceso: ADMIN y USER
     */
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("/{id}/sequence")
    public ResponseEntity<String> obtenerSecuenciaCompleta(@PathVariable Long id) {
        return chromosomeService.obtenerSecuenciaCompleta(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * GET /chromosomes/{id}/sequence/range?start=&end= → Consultar una subsecuencia por rango.
     * Acceso: ADMIN y USER
     */
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("/{id}/sequence/range")
    public ResponseEntity<String> obtenerSubsecuencia(
            @PathVariable Long id,
            @RequestParam Long start,
            @RequestParam Long end) {

        return chromosomeService.obtenerSubsecuenciaPorRango(id, start, end)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * PUT /chromosomes/{id}/sequence → Registrar o actualizar la secuencia.
     * Acceso: Solo ADMIN
     */
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}/sequence")
    public ResponseEntity<ChromosomeOutDTO> actualizarSecuencia(
            @PathVariable Long id,
            @RequestBody String nuevaSecuencia) {

        return chromosomeService.registrarOActualizarSecuencia(id, nuevaSecuencia)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
