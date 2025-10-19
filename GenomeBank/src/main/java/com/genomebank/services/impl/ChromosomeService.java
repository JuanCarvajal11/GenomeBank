package com.genomebank.services.impl;

import com.genomebank.dtos.ChromosomeInDTO;
import com.genomebank.dtos.ChromosomeOutDTO;
import com.genomebank.entities.Chromosome;
import com.genomebank.entities.Genome;
import com.genomebank.repositories.ChromosomeRepository;
import com.genomebank.repositories.GenomeRepository;
import com.genomebank.services.IChromosomeService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
        return chromosomeRepository.findAll().stream()
                .map(this::convertirAOutDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<ChromosomeOutDTO> obtenerCromosomaPorId(Long id) {
        return chromosomeRepository.findById(id).map(this::convertirAOutDTO);
    }

    @Override
    public ChromosomeOutDTO crearCromosoma(ChromosomeInDTO chromosomeInDTO) {
        Genome genome = genomeRepository.getReferenceById(chromosomeInDTO.getGenomeId());
        Chromosome c = new Chromosome();
        c.setName(chromosomeInDTO.getName());
        c.setLength(chromosomeInDTO.getLength());
        c.setSequence(chromosomeInDTO.getSequence());
        c.setGenome(genome);
        return convertirAOutDTO(chromosomeRepository.save(c));
    }

    @Override
    public Optional<ChromosomeOutDTO> actualizarCromosoma(Long id, ChromosomeInDTO chromosomeInDTO) {
        return chromosomeRepository.findById(id).map(cEncontrado -> {
            Genome genome = genomeRepository.getReferenceById(chromosomeInDTO.getGenomeId());
            cEncontrado.setName(chromosomeInDTO.getName());
            cEncontrado.setLength(chromosomeInDTO.getLength());
            cEncontrado.setSequence(chromosomeInDTO.getSequence());
            cEncontrado.setGenome(genome);
            return convertirAOutDTO(chromosomeRepository.save(cEncontrado));
        });
    }

    @Override
    public void eliminarCromosoma(Long id) {
        chromosomeRepository.deleteById(id);
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
