package com.genomebank.services.impl;

import com.genomebank.dtos.ChromosomeInDTO;
import com.genomebank.entities.Chromosome;
import com.genomebank.entities.Genome;
import com.genomebank.repositories.ChromosomeRepository;
import com.genomebank.repositories.GenomeRepository;
import com.genomebank.services.IChromosomeService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ChromosomeService implements IChromosomeService {

    private final ChromosomeRepository chromosomeRepository;
    private final GenomeRepository genomeRepository;

    public ChromosomeService(ChromosomeRepository chromosomeRepository, GenomeRepository genomeRepository) {
        this.chromosomeRepository = chromosomeRepository;
        this.genomeRepository = genomeRepository;
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
    public Chromosome crearCromosoma(ChromosomeInDTO chromosomeInDTO) {
        Genome genome = genomeRepository.getReferenceById(chromosomeInDTO.getGenomeId());
        Chromosome c = new Chromosome();
        c.setName(chromosomeInDTO.getName());
        c.setLength(chromosomeInDTO.getLength());
        c.setSequence(chromosomeInDTO.getSequence());
        c.setGenome(genome);
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
