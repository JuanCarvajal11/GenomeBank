package com.genomebank.controllers;

import com.genomebank.entities.Gene;
import com.genomebank.services.IGeneService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/gene")
public class GeneController {

    private final IGeneService geneService;

    public GeneController(IGeneService geneService) {
        this.geneService = geneService;
    }

    /**
     * Crear un nuevo gen.
     */
    @PostMapping("/crear")
    public ResponseEntity<Gene> crearGene(@RequestBody Gene gene) {
        return ResponseEntity.ok(geneService.crearGen(gene));
    }

    /**
     * Actualizar completamente un gen.
     */
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<Gene> actualizarGene(@PathVariable Long id, @RequestBody Gene gene) {
        return geneService.actualizarGen(id, gene)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Actualizar parcialmente un gen.
     */
    @PatchMapping("/actualizar_parcial/{id}")
    public ResponseEntity<Gene> actualizarGeneParcial(@PathVariable Long id, @RequestBody Gene gene) {
        return geneService.actualizarGen(id, gene)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Consultar todos los genes.
     */
    @GetMapping("/consultar_todos")
    public ResponseEntity<List<Gene>> obtenerGenes() {
        return ResponseEntity.ok(geneService.obtenerGenes());
    }

    /**
     * Consultar un gen por su ID.
     */
    @GetMapping("/consultar/{id}")
    public ResponseEntity<Gene> obtenerPorId(@PathVariable Long id) {
        return geneService.obtenerGenPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Eliminar un gen.
     */
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminarGene(@PathVariable Long id) {
        geneService.eliminarGen(id);
        return ResponseEntity.noContent().build();
    }
}
