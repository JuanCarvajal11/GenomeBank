package com.genomebank.services.impl;

import com.genomebank.dtos.GenomeInDTO;
import com.genomebank.dtos.GenomeOutDTO;
import com.genomebank.entities.Genome;
import com.genomebank.entities.Species;
import com.genomebank.repositories.GenomeRepository;
import com.genomebank.repositories.SpeciesRepository;
import com.genomebank.services.IGenomeService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        List<Genome> genomes = genomeRepository.findAll();
        List<GenomeOutDTO> genomesOut = new ArrayList<>();

        for (Genome genome : genomes) {
            GenomeOutDTO og = new GenomeOutDTO();
            og.setId(genome.getId());
            og.setVersionName(genome.getVersionName());
            og.setSpeciesName(genome.getSpecies().getScientificName());
            genomesOut.add(og);
        }

        return genomesOut;
    }

    @Override
    public List<GenomeOutDTO> obtenerGenomasPorEspecie(Long speciesId) {
        List<Genome> genomes = genomeRepository.findAll();
        List<GenomeOutDTO> genomesOut = new ArrayList<>();

        for (Genome genome : genomes) {
            if (genome.getSpecies() != null && genome.getSpecies().getId().equals(speciesId)) {
                GenomeOutDTO og = new GenomeOutDTO();
                og.setId(genome.getId());
                og.setVersionName(genome.getVersionName());
                og.setSpeciesName(genome.getSpecies().getScientificName());
                genomesOut.add(og);
            }
        }

        return genomesOut;
    }

    @Override
    public Optional<GenomeOutDTO> obtenerGenomaPorId(Long id) {
        Optional<Genome> genomeOptional = genomeRepository.findById(id);

        if (genomeOptional.isPresent()) {
            Genome genome = genomeOptional.get();
            GenomeOutDTO ogp = new GenomeOutDTO();
            ogp.setId(genome.getId());
            ogp.setVersionName(genome.getVersionName());
            ogp.setSpeciesName(genome.getSpecies().getScientificName());
            return Optional.of(ogp);
        }

        return Optional.empty();
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
        Optional<Genome> genomeOptional = genomeRepository.findById(id);

        if (genomeOptional.isPresent()) {
            Genome gEncontrado = genomeOptional.get();
            Species species = speciesRepository.getReferenceById(genomeInDTO.getSpeciesId());

            gEncontrado.setVersionName(genomeInDTO.getVersionName());
            gEncontrado.setSpecies(species);

            Genome savedGenome = genomeRepository.save(gEncontrado);

            GenomeOutDTO ag = new GenomeOutDTO();
            ag.setId(savedGenome.getId());
            ag.setVersionName(savedGenome.getVersionName());
            ag.setSpeciesName(savedGenome.getSpecies().getScientificName());

            return Optional.of(ag);
        }

        return Optional.empty();
    }

    @Override
    public Optional<GenomeOutDTO> eliminarGenoma(Long id) {
        Optional<Genome> genomeOptional = genomeRepository.findById(id);

        if (genomeOptional.isPresent()) {
            Genome genome = genomeOptional.get();

            GenomeOutDTO eg = new GenomeOutDTO();
            eg.setId(genome.getId());
            eg.setVersionName(genome.getVersionName());
            eg.setSpeciesName(genome.getSpecies().getScientificName());

            genomeRepository.deleteById(id);

            return Optional.of(eg);
        }

        return Optional.empty();
    }
}
