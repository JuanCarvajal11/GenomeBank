package com.genomebank.controllers;

import com.genomebank.entities.Species;
import com.genomebank.services.ISpeciesService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/species")
public class SpeciesController {

    private final ISpeciesService speciesService;

    public SpeciesController(ISpeciesService speciesService) {
        this.speciesService = speciesService;
    }

    /**
     * Crear una nueva especie.
     */
    @PostMapping("/crear")
    public ResponseEntity<Species> crearEspecie(@RequestBody Species species) {
        return ResponseEntity.ok(speciesService.crearEspecie(species));
    }

    /**
     * Actualizar una especie completamente.
     */
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<Species> actualizarEspecie(@PathVariable Long id, @RequestBody Species species) {
        return speciesService.actualizarEspecie(id, species)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Actualizar parcialmente una especie.
     */
    @PatchMapping("/actualizar_parcial/{id}")
    public ResponseEntity<Species> actualizarEspecieParcial(@PathVariable Long id, @RequestBody Species species) {
        return speciesService.actualizarEspecie(id, species)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Consultar todas las especies.
     */
    @GetMapping("/consultar_todos")
    public ResponseEntity<List<Species>> obtenerEspecies() {
        return ResponseEntity.ok(speciesService.obtenerEspecies());
    }

    /**
     * Consultar una especie por ID.
     */
    @GetMapping("/consultar/{id}")
    public ResponseEntity<Species> obtenerPorId(@PathVariable Long id) {
        return speciesService.obtenerEspeciesPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Eliminar una especie.
     */
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminarEspecie(@PathVariable Long id) {
        speciesService.eliminarEspecie(id);
        return ResponseEntity.noContent().build();
    }
}
