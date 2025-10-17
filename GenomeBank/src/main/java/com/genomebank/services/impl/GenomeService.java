package com.genomebank.services.impl;

import com.genomebank.entities.Genome;
import com.genomebank.repositories.GenomeRepository;
import com.genomebank.services.IGenomeService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GenomeService implements IGenomeService {

    private final GenomeRepository genomeRepository;

    public GenomeService(GenomeRepository genomeRepository) {
        this.genomeRepository = genomeRepository;
    }

    @Override
    public List<Genome> obtenerGenomas() {
        return genomeRepository.findAll();
    }

    @Override
    public Optional<Genome> obtenerGenomaPorId(Long id) {
        return genomeRepository.findById(id);
    }

    @Override
    public Genome crearGenoma(Genome genome) {
        Genome g = new Genome();
        g.setVersionName(genome.getVersionName());
        g.setSpecies(genome.getSpecies());
        return genomeRepository.save(g);
    }

    @Override
    public Optional<Genome> actualizarGenoma(Long id, Genome genome) {
        return genomeRepository.findById(id).map(gEncontrado -> {
            gEncontrado.setVersionName(genome.getVersionName());
            gEncontrado.setSpecies(genome.getSpecies());
            return genomeRepository.save(gEncontrado);
        });
    }

    @Override
    public void eliminarGenoma(Long id) {
        genomeRepository.deleteById(id);
    }
}
