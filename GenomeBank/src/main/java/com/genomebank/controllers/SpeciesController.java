package com.genomebank.controllers;

import com.genomebank.dtos.SpeciesInDTO;
import com.genomebank.dtos.SpeciesOutDTO;
import com.genomebank.services.ISpeciesService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Controlador REST para gestionar especies biológicas.
 * Proporciona endpoints para operaciones CRUD.
 */
@RestController
@RequestMapping("/species")
public class SpeciesController {

    private final ISpeciesService speciesService;

    public SpeciesController(ISpeciesService speciesService) {
        this.speciesService = speciesService;
    }

    /**
     * Consultar todas las especies.
     * @return ResponseEntity con la lista de todas las especies
     */
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping
    public ResponseEntity<List<SpeciesOutDTO>> obtenerEspecies() {
        List<SpeciesOutDTO> especies = speciesService.obtenerEspecies();
        return ResponseEntity.ok(especies);
    }

    /**
     * Consultar una especie por ID.
     * @param id ID de la especie
     * @return ResponseEntity con la especie o 404 si no existe
     */
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("/{id}")
    public ResponseEntity<SpeciesOutDTO> obtenerPorId(@PathVariable Long id) {
        Optional<SpeciesOutDTO> speciesOpt = speciesService.obtenerEspeciePorId(id);

        if (speciesOpt.isPresent()) {
            return ResponseEntity.ok(speciesOpt.get());
        }

        return ResponseEntity.notFound().build();
    }

    /**
     * Crear una nueva especie.
     * @param speciesInDTO Datos de la especie a crear
     * @return ResponseEntity con la especie creada y status 201
     */
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<SpeciesOutDTO> crearEspecie(@RequestBody SpeciesInDTO speciesInDTO) {
        SpeciesOutDTO creada = speciesService.crearEspecie(speciesInDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(creada);
    }

    /**
     * Actualizar una especie completamente.
     * @param id ID de la especie a actualizar
     * @param speciesInDTO Datos actualizados de la especie
     * @return ResponseEntity con la especie actualizada o 404 si no existe
     */
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<SpeciesOutDTO> actualizarEspecie(
            @PathVariable Long id,
            @RequestBody SpeciesInDTO speciesInDTO) {
        Optional<SpeciesOutDTO> actualizada = speciesService.actualizarEspecie(id, speciesInDTO);

        if (actualizada.isPresent()) {
            return ResponseEntity.ok(actualizada.get());
        }

        return ResponseEntity.notFound().build();
    }

    /**
     * Eliminar una especie.
     * @param id ID de la especie a eliminar
     * @return ResponseEntity con status 204 si se eliminó correctamente
     */
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarEspecie(@PathVariable Long id) {
        speciesService.eliminarEspecie(id);
        return ResponseEntity.noContent().build();
    }
}
