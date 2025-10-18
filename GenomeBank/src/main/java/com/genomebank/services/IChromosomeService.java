package com.genomebank.services;

import com.genomebank.dtos.ChromosomeInDTO;
import com.genomebank.entities.Chromosome;

import java.util.List;
import java.util.Optional;

public interface IChromosomeService {
    public Chromosome crearCromosoma(ChromosomeInDTO chromosomeInDTO);

    public List<Chromosome> obtenerCromosomas();

    public Optional<Chromosome> obtenerCromosomaPorId(Long id);

    public Optional<Chromosome> actualizarCromosoma(Long id, Chromosome chromosome);

    public void eliminarCromosoma(Long id);
}
