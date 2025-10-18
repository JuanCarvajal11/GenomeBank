package com.genomebank.controllers;

import com.genomebank.dtos.ChromosomeInDTO;
import com.genomebank.entities.Chromosome;
import com.genomebank.services.IChromosomeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chromosome")
public class ChromosomeController {

    private final IChromosomeService chromosomeService;

    public ChromosomeController(IChromosomeService chromosomeService) {
        this.chromosomeService = chromosomeService;
    }

    /**
     * Obtener todos los cromosomas registrados.
     * @return Lista de cromosomas.
     */
    @GetMapping("/")
    public ResponseEntity<List<Chromosome>> obtenerChromosomes() {
        return ResponseEntity.ok(chromosomeService.obtenerCromosomas());
    }

    /**
     * Consultar un cromosoma específico por su ID.
     * @param id ID del cromosoma.
     * @return El cromosoma encontrado o 404 si no existe.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Chromosome> obtenerPorId(@PathVariable Long id) {
        return chromosomeService.obtenerCromosomaPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Crear un nuevo cromosoma.
     * @param chromosomeInDTO Objeto cromosoma recibido en el cuerpo de la petición.
     * @return El cromosoma creado.
     */
    @PostMapping("/create")
    public ResponseEntity<Chromosome> crearChromosome(@RequestBody ChromosomeInDTO chromosomeInDTO) {
        return ResponseEntity.ok(chromosomeService.crearCromosoma(chromosomeInDTO));
    }

    /**
     * Actualizar completamente un cromosoma existente.
     * @param id ID del cromosoma a actualizar.
     * @param chromosome Nuevos datos del cromosoma.
     * @return El cromosoma actualizado o 404 si no se encuentra.
     */
    @PutMapping("/update/{id}")
    public ResponseEntity<Chromosome> actualizarChromosome(@PathVariable Long id,
                                                           @RequestBody Chromosome chromosome) {
        return chromosomeService.actualizarCromosoma(id, chromosome)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Eliminar un cromosoma por su ID.
     * @param id ID del cromosoma a eliminar.
     * @return Respuesta sin contenido (204) si se elimina correctamente.
     */
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminarChromosome(@PathVariable Long id) {
        chromosomeService.eliminarCromosoma(id);
        return ResponseEntity.noContent().build();
    }
}
