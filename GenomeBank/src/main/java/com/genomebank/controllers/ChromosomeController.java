package com.genomebank.controllers;

import com.genomebank.dtos.ChromosomeInDTO;
import com.genomebank.dtos.ChromosomeOutDTO;
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

    @GetMapping("/")
    public ResponseEntity<List<ChromosomeOutDTO>> obtenerChromosomes() {
        return ResponseEntity.ok(chromosomeService.obtenerCromosomas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ChromosomeOutDTO> obtenerPorId(@PathVariable Long id) {
        return chromosomeService.obtenerCromosomaPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/create")
    public ResponseEntity<ChromosomeOutDTO> crearChromosome(@RequestBody ChromosomeInDTO chromosomeInDTO) {
        return ResponseEntity.ok(chromosomeService.crearCromosoma(chromosomeInDTO));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ChromosomeOutDTO> actualizarChromosome(@PathVariable Long id,
                                                                 @RequestBody ChromosomeInDTO chromosomeInDTO) {
        return chromosomeService.actualizarCromosoma(id, chromosomeInDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> eliminarChromosome(@PathVariable Long id) {
        chromosomeService.eliminarCromosoma(id);
        return ResponseEntity.noContent().build();
    }
}
