package com.genomebank.services.impl;

import com.genomebank.entities.Chromosome;
import com.genomebank.repositories.ChromosomeRepository;
import com.genomebank.services.IChromosomeService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ChromosomeService implements IChromosomeService {

    private final ChromosomeRepository chromosomeRepository;

    public ChromosomeService(ChromosomeRepository chromosomeRepository) {
        this.chromosomeRepository = chromosomeRepository;
    }

    @Override
    public List<Chromosome> obtenerCromosomas() {
        return chromosomeRepository.findAll();
    }

    @Override
    public Optional<Chromosome> obtenerCromosomaPorId(Long id) {
        return chromosomeRepository.findById(id);
    }

    @Override
    public Chromosome crearCromosoma(Chromosome chromosome) {
        Chromosome c = new Chromosome();
        c.setName(chromosome.getName());
        c.setLength(chromosome.getLength());
        c.setSequence(chromosome.getSequence());
        c.setGenome(chromosome.getGenome());
        return chromosomeRepository.save(c);
    }

    @Override
    public Optional<Chromosome> actualizarCromosoma(Long id, Chromosome chromosome) {
        return chromosomeRepository.findById(id).map(cEncontrado -> {
            cEncontrado.setName(chromosome.getName());
            cEncontrado.setLength(chromosome.getLength());
            cEncontrado.setSequence(chromosome.getSequence());
            cEncontrado.setGenome(chromosome.getGenome());
            return chromosomeRepository.save(cEncontrado);
        });
    }

    @Override
    public void eliminarCromosoma(Long id) {
        chromosomeRepository.deleteById(id);
    }
}
