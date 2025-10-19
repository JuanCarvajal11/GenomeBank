package com.genomebank.controllers;

import com.genomebank.dtos.GeneFunctionInDTO;
import com.genomebank.dtos.GeneFunctionOutDTO;
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
    public ResponseEntity<GeneFunctionOutDTO> crearGene(@RequestBody GeneFunctionInDTO geneInDTO) {
        return ResponseEntity.ok(geneService.crearGen(geneInDTO));
    }

    /**
     * Actualizar completamente un gen.
     */
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<GeneFunctionOutDTO> actualizarGene(@PathVariable Long id, @RequestBody GeneFunctionInDTO geneInDTO) {
        return geneService.actualizarGen(id, geneInDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Consultar todos los genes.
     */
    @GetMapping("/consultar_todos")
    public ResponseEntity<List<GeneFunctionOutDTO>> obtenerGenes() {
        return ResponseEntity.ok(geneService.obtenerGenes());
    }

    /**
     * Consultar un gen por su ID.
     */
    @GetMapping("/consultar/{id}")
    public ResponseEntity<GeneFunctionOutDTO> obtenerPorId(@PathVariable Long id) {
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
