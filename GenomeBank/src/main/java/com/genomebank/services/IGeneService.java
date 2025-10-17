package com.genomebank.services;

import com.genomebank.entities.Gene;

import java.util.List;
import java.util.Optional;

public interface IGeneService {
    public List<Gene> obtenerGenes();

    public Optional<Gene> obtenerGenPorId(Long id);

    public Gene crearGen(Gene gene);

    public Optional<Gene> actualizarGen(Long id, Gene gene);

    public void eliminarGen(Long id);
}
