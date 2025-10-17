package com.genomebank.controllers;

import com.genomebank.entities.Genome;
import com.genomebank.services.IGenomeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/genome")
public class GenomeController {

    private final IGenomeService genomeService;

    public GenomeController(IGenomeService genomeService) {
        this.genomeService = genomeService;
    }

    /**
     * Endpoint para crear un nuevo genome.
     * @param genome Objeto Genome a crear.
     * @return ResponseEntity con el genome creado.
     */
    @PostMapping("/crear")
    public ResponseEntity<Genome> crearGenome(@RequestBody Genome genome) {
        return ResponseEntity.ok(this.genomeService.crearGenoma(genome));
    }

    /**
     * Endpoint para actualizar un genome completo.
     * @param id ID del genome a actualizar.
     * @param genome Genome con datos actualizados.
     * @return ResponseEntity con el genome actualizado o 404 si no existe.
     */
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<Genome> actualizarGenome(@PathVariable Long id,
                                                   @RequestBody Genome genome) {
        return this.genomeService.actualizarGenoma(id, genome)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Endpoint para actualizar parcialmente un genome.
     * @param id ID del genome a actualizar.
     * @param genome Genome con campos a actualizar.
     * @return ResponseEntity con el genome actualizado o 404 si no existe.
     */
    /*@PatchMapping("/actualizar_parcial/{id}")
    public ResponseEntity<Genome> actualizarGenomeParcial(@PathVariable Long id,
                                                          @RequestBody Genome genome) {
        return this.genomeService.actualizarGenomeParcial(id, genome)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }*/

    /**
     * Endpoint para obtener todos los genomes.
     * @return ResponseEntity con la lista de genomes.
     */
    @GetMapping("/consultar_todos")
    public ResponseEntity<List<Genome>> obtenerGenomes() {
        return ResponseEntity.ok(genomeService.obtenerGenomas());
    }

    /**
     * Endpoint para obtener un genome por ID.
     * @param id ID del genome.
     * @return ResponseEntity con el genome o 404 si no existe.
     */
    @GetMapping("/consultar/{id}")
    public ResponseEntity<Genome> consultarPorId(@PathVariable Long id) {
        return this.genomeService.obtenerGenomaPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Endpoint para eliminar un genome.
     * @param id ID del genome a eliminar.
     * @return ResponseEntity vacío o 404 si no existe.
     */
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminarGenome(@PathVariable Long id) {
        this.genomeService.eliminarGenoma(id);
        return ResponseEntity.ok().build();
    }
}
