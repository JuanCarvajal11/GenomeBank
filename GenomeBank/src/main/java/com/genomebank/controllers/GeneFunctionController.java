package com.genomebank.controllers;

import com.genomebank.entities.GeneFunction;
import com.genomebank.services.IGeneFunctionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/gene_function")
public class GeneFunctionController {

    private final IGeneFunctionService geneFunctionService;

    public GeneFunctionController(IGeneFunctionService geneFunctionService) {
        this.geneFunctionService = geneFunctionService;
    }

    /**
     * Crear una nueva relación gen-función.
     */
    @PostMapping("/crear")
    public ResponseEntity<GeneFunction> crearRelacion(@RequestBody GeneFunction relation) {
        return ResponseEntity.ok(geneFunctionService.crearRelacion(relation));
    }

    /**
     * Actualizar una relación completamente.
     */
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<GeneFunction> actualizarRelacion(@PathVariable Long id, @RequestBody GeneFunction relation) {
        return geneFunctionService.actualizarRelacion(id, relation)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Actualizar parcialmente una relación.
     */
    @PatchMapping("/actualizar_parcial/{id}")
    public ResponseEntity<GeneFunction> actualizarRelacionParcial(@PathVariable Long id, @RequestBody GeneFunction relation) {
        return geneFunctionService.actualizarRelacion(id, relation)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Consultar todas las relaciones.
     */
    @GetMapping("/consultar_todos")
    public ResponseEntity<List<GeneFunction>> obtenerRelaciones() {
        return ResponseEntity.ok(geneFunctionService.obtenerRelaciones());
    }

    /**
     * Consultar una relación por su ID.
     */
    @GetMapping("/consultar/{id}")
    public ResponseEntity<GeneFunction> obtenerPorId(@PathVariable Long id) {
        return geneFunctionService.obtenerRelacionPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Eliminar una relación.
     */
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminarRelacion(@PathVariable Long id) {
        geneFunctionService.eliminarRelacion(id);
        return ResponseEntity.noContent().build();
    }
}
