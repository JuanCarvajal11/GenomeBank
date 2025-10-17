package com.genomebank.services.impl;

import com.genomebank.entities.Gene;
import com.genomebank.repositories.GeneRepository;
import com.genomebank.services.IGeneService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GeneService implements IGeneService {

    private final GeneRepository geneRepository;

    public GeneService(GeneRepository geneRepository) {
        this.geneRepository = geneRepository;
    }

    @Override
    public List<Gene> obtenerGenes() {
        return geneRepository.findAll();
    }

    @Override
    public Optional<Gene> obtenerGenPorId(Long id) {
        return geneRepository.findById(id);
    }

    @Override
    public Gene crearGen(Gene gene) {
        Gene g = new Gene();
        g.setSymbol(gene.getSymbol());
        g.setStartPosition(gene.getStartPosition());
        g.setEndPosition(gene.getEndPosition());
        g.setStrand(gene.getStrand());
        g.setSequence(gene.getSequence());
        g.setChromosome(gene.getChromosome());
        return geneRepository.save(g);
    }

    @Override
    public Optional<Gene> actualizarGen(Long id, Gene gene) {
        return geneRepository.findById(id).map(gEncontrado -> {
            gEncontrado.setSymbol(gene.getSymbol());
            gEncontrado.setStartPosition(gene.getStartPosition());
            gEncontrado.setEndPosition(gene.getEndPosition());
            gEncontrado.setStrand(gene.getStrand());
            gEncontrado.setSequence(gene.getSequence());
            gEncontrado.setChromosome(gene.getChromosome());
            return geneRepository.save(gEncontrado);
        });
    }

    @Override
    public void eliminarGen(Long id) {
        geneRepository.deleteById(id);
    }
}
