package com.genomebank.services.impl;

import com.genomebank.dtos.ChromosomeInDTO;
import com.genomebank.dtos.ChromosomeOutDTO;
import com.genomebank.entities.Chromosome;
import com.genomebank.entities.Genome;
import com.genomebank.repositories.ChromosomeRepository;
import com.genomebank.repositories.GenomeRepository;
import com.genomebank.services.IChromosomeService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    public List<ChromosomeOutDTO> obtenerCromosomas() {
        List<Chromosome> chromosomes = chromosomeRepository.findAll();
        List<ChromosomeOutDTO> chromosomesOut = new ArrayList<>();

        for (Chromosome chromosome : chromosomes) {
            chromosomesOut.add(convertirAOutDTO(chromosome));
        }

        return chromosomesOut;
    }

    @Override
    public List<ChromosomeOutDTO> obtenerCromosomasPorGenoma(Long genomeId) {
        List<Chromosome> chromosomes = chromosomeRepository.findAll();
        List<ChromosomeOutDTO> chromosomesOut = new ArrayList<>();

        for (Chromosome chromosome : chromosomes) {
            if (chromosome.getGenome() != null && chromosome.getGenome().getId().equals(genomeId)) {
                chromosomesOut.add(convertirAOutDTO(chromosome));
            }
        }

        return chromosomesOut;
    }

    @Override
    public Optional<ChromosomeOutDTO> obtenerCromosomaPorId(Long id) {
        Optional<Chromosome> chromosomeOptional = chromosomeRepository.findById(id);

        if (chromosomeOptional.isPresent()) {
            Chromosome chromosome = chromosomeOptional.get();
            return Optional.of(convertirAOutDTO(chromosome));
        }

        return Optional.empty();
    }

    @Override
    public ChromosomeOutDTO crearCromosoma(ChromosomeInDTO chromosomeInDTO) {
        Genome genome = genomeRepository.getReferenceById(chromosomeInDTO.getGenomeId());

        Chromosome c = new Chromosome();
        c.setName(chromosomeInDTO.getName());
        c.setLength(chromosomeInDTO.getLength());
        c.setSequence(chromosomeInDTO.getSequence());
        c.setGenome(genome);

        Chromosome savedChromosome = chromosomeRepository.save(c);
        return convertirAOutDTO(savedChromosome);
    }

    @Override
    public Optional<ChromosomeOutDTO> actualizarCromosoma(Long id, ChromosomeInDTO chromosomeInDTO) {
        Optional<Chromosome> chromosomeOptional = chromosomeRepository.findById(id);

        if (chromosomeOptional.isPresent()) {
            Chromosome cEncontrado = chromosomeOptional.get();
            Genome genome = genomeRepository.getReferenceById(chromosomeInDTO.getGenomeId());

            cEncontrado.setName(chromosomeInDTO.getName());
            cEncontrado.setLength(chromosomeInDTO.getLength());
            cEncontrado.setSequence(chromosomeInDTO.getSequence());
            cEncontrado.setGenome(genome);

            Chromosome savedChromosome = chromosomeRepository.save(cEncontrado);
            return Optional.of(convertirAOutDTO(savedChromosome));
        }

        return Optional.empty();
    }

    @Override
    public Optional<ChromosomeOutDTO> eliminarCromosoma(Long id) {
        Optional<Chromosome> chromosomeOptional = chromosomeRepository.findById(id);

        if (chromosomeOptional.isPresent()) {
            Chromosome chromosome = chromosomeOptional.get();
            ChromosomeOutDTO chromosomeOutDTO = convertirAOutDTO(chromosome);

            chromosomeRepository.deleteById(id);

            return Optional.of(chromosomeOutDTO);
        }

        return Optional.empty();
    }

    private ChromosomeOutDTO convertirAOutDTO(Chromosome chromosome) {
        ChromosomeOutDTO dto = new ChromosomeOutDTO();
        dto.setId(chromosome.getId());
        dto.setName(chromosome.getName());
        dto.setLength(chromosome.getLength());
        dto.setGenomeVersion(chromosome.getGenome().getVersionName());
        return dto;
    }
}