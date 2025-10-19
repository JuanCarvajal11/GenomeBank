package com.genomebank.services.impl;

import com.genomebank.dtos.GenomeInDTO;
import com.genomebank.dtos.GenomeOutDTO;
import com.genomebank.entities.Genome;
import com.genomebank.entities.Species;
import com.genomebank.repositories.GenomeRepository;
import com.genomebank.repositories.SpeciesRepository;
import com.genomebank.services.IGenomeService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GenomeService implements IGenomeService {

    private final GenomeRepository genomeRepository;
    private final SpeciesRepository speciesRepository;

    public GenomeService(GenomeRepository genomeRepository, SpeciesRepository speciesRepository) {
        this.genomeRepository = genomeRepository;
        this.speciesRepository = speciesRepository;
    }

    @Override
    public List<GenomeOutDTO> obtenerGenomas() {
        return genomeRepository.findAll().stream()
                .map(genome -> {
                    GenomeOutDTO og = new GenomeOutDTO();
                    og.setId(genome.getId());
                    og.setVersionName(genome.getVersionName());
                    og.setSpeciesName(genome.getSpecies().getScientificName());
                    return og;
                })
                .collect(Collectors.toList());
    }

    @Override
    public Optional<GenomeOutDTO> obtenerGenomaPorId(Long id) {
        return genomeRepository.findById(id).map(genome -> {
            GenomeOutDTO ogp = new GenomeOutDTO();
            ogp.setId(genome.getId());
            ogp.setVersionName(genome.getVersionName());
            ogp.setSpeciesName(genome.getSpecies().getScientificName());
            return ogp;
        });
    }

    @Override
    public GenomeOutDTO crearGenoma(GenomeInDTO genomeInDTO) {
        Species species = speciesRepository.getReferenceById(genomeInDTO.getSpeciesId());
        Genome g = new Genome();
        g.setVersionName(genomeInDTO.getVersionName());
        g.setSpecies(species);
        Genome savedGenome = genomeRepository.save(g);
        
        GenomeOutDTO cg = new GenomeOutDTO();
        cg.setId(savedGenome.getId());
        cg.setVersionName(savedGenome.getVersionName());
        cg.setSpeciesName(savedGenome.getSpecies().getScientificName());
        return cg;
    }

    @Override
    public Optional<GenomeOutDTO> actualizarGenoma(Long id, GenomeInDTO genomeInDTO) {
        return genomeRepository.findById(id).map(gEncontrado -> {
            Species species = speciesRepository.getReferenceById(genomeInDTO.getSpeciesId());
            gEncontrado.setVersionName(genomeInDTO.getVersionName());
            gEncontrado.setSpecies(species);
            Genome savedGenome = genomeRepository.save(gEncontrado);
            
            GenomeOutDTO ag = new GenomeOutDTO();
            ag.setId(savedGenome.getId());
            ag.setVersionName(savedGenome.getVersionName());
            ag.setSpeciesName(savedGenome.getSpecies().getScientificName());
            return ag;
        });
    }

    @Override
    public Optional<GenomeOutDTO> eliminarGenoma(Long id) {
        return genomeRepository.findById(id).map(genome -> {
            GenomeOutDTO eg = new GenomeOutDTO();
            eg.setId(genome.getId());
            eg.setVersionName(genome.getVersionName());
            eg.setSpeciesName(genome.getSpecies().getScientificName());
            genomeRepository.deleteById(id);
            return eg;
        });
    }
}
