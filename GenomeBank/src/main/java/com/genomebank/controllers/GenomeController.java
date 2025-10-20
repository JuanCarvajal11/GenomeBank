package com.genomebank.controllers;

import com.genomebank.dtos.GenomeInDTO;
import com.genomebank.dtos.GenomeOutDTO;
import com.genomebank.services.IGenomeService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/genomes")
public class GenomeController {

    private final IGenomeService genomeService;

    public GenomeController(IGenomeService genomeService) {
        this.genomeService = genomeService;
    }

    /**
     * GET /genomes → Listar todos los genomas o filtrar por especie (?speciesId=).
     * @param speciesId ID de la especie para filtrar (opcional).
     * @return ResponseEntity con la lista de genomas.
     */
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping
    public ResponseEntity<List<GenomeOutDTO>> obtenerGenomes(@RequestParam(required = false) Long speciesId) {
        if (speciesId != null) {
            return ResponseEntity.ok(genomeService.obtenerGenomasPorEspecie(speciesId));
        }
        return ResponseEntity.ok(genomeService.obtenerGenomas());
    }

    /**
     * Endpoint para obtener un genome por ID.
     * @param id ID del genome.
     * @return ResponseEntity con el genome o 404 si no existe.
     */
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("/{id}")
    public ResponseEntity<GenomeOutDTO> consultarPorId(@PathVariable Long id) {
        return this.genomeService.obtenerGenomaPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * POST /genomes → Crear un nuevo genoma (solo ADMIN).
     * @param genomeInDTO Objeto GenomeInDTO a crear.
     * @return ResponseEntity con el genoma creado.
     */
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<GenomeOutDTO> crearGenome(@RequestBody GenomeInDTO genomeInDTO) {
        return ResponseEntity.ok(this.genomeService.crearGenoma(genomeInDTO));
    }

    /**
     * PUT /genomes/{id} → Actualizar un genoma (solo ADMIN).
     * @param id ID del genoma a actualizar.
     * @param genomeInDTO GenomeInDTO con datos actualizados.
     * @return ResponseEntity con el genoma actualizado o 404 si no existe.
     */
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<GenomeOutDTO> actualizarGenome(
            @PathVariable Long id,
            @RequestBody GenomeInDTO genomeInDTO) {

        return this.genomeService.actualizarGenoma(id, genomeInDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * DELETE /genomes/{id} → Eliminar un genoma (solo ADMIN).
     * @param id ID del genoma a eliminar.
     * @return ResponseEntity con el genoma eliminado o 404 si no existe.
     */
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<GenomeOutDTO> eliminarGenome(@PathVariable Long id) {
        return this.genomeService.eliminarGenoma(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
