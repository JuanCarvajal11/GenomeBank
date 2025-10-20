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
        return chromosomeRepository.findAll()
                .stream()
                .map(this::convertirAOutDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ChromosomeOutDTO> obtenerCromosomasPorGenoma(Long genomeId) {
        return chromosomeRepository.findByGenomeId(genomeId)
                .stream()
                .map(this::convertirAOutDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<ChromosomeOutDTO> obtenerCromosomaPorId(Long id) {
        return chromosomeRepository.findById(id)
                .map(this::convertirAOutDTO);
    }

    @Override
    public ChromosomeOutDTO crearCromosoma(ChromosomeInDTO chromosomeInDTO) {
        Chromosome chromosome = new Chromosome();

        if (chromosomeInDTO.getGenomeId() != null) {
            Genome genome = genomeRepository.findById(chromosomeInDTO.getGenomeId())
                    .orElseThrow(() -> new RuntimeException("Genome no encontrado"));
            chromosome.setGenome(genome);
        }

        chromosome.setName(chromosomeInDTO.getName());
        chromosome.setLength(chromosomeInDTO.getLength());
        chromosome.setSequence(chromosomeInDTO.getSequence());

        return convertirAOutDTO(chromosomeRepository.save(chromosome));
    }

    @Override
    public Optional<ChromosomeOutDTO> actualizarCromosoma(Long id, ChromosomeInDTO chromosomeInDTO) {
        return chromosomeRepository.findById(id).map(chromosome -> {

            if (chromosomeInDTO.getGenomeId() != null) {
                Genome genome = genomeRepository.findById(chromosomeInDTO.getGenomeId())
                        .orElseThrow(() -> new RuntimeException("Genome no encontrado"));
                chromosome.setGenome(genome);
            }

            chromosome.setName(chromosomeInDTO.getName());
            chromosome.setLength(chromosomeInDTO.getLength());
            chromosome.setSequence(chromosomeInDTO.getSequence());

            return convertirAOutDTO(chromosomeRepository.save(chromosome));
        });
    }

    @Override
    public Optional<ChromosomeOutDTO> eliminarCromosoma(Long id) {
        return chromosomeRepository.findById(id).map(chromosome -> {
            chromosomeRepository.delete(chromosome);
            return convertirAOutDTO(chromosome);
        });
    }


    @Override
    public Optional<String> obtenerSecuenciaCompleta(Long id) {
        return chromosomeRepository.findById(id)
                .map(Chromosome::getSequence);
    }

    @Override
    public Optional<String> obtenerSubsecuenciaPorRango(Long id, Long start, Long end) {
        return chromosomeRepository.findById(id)
                .map(chromosome -> {
                    String sequence = chromosome.getSequence();
                    if (sequence == null || sequence.isEmpty()) return "";

                    int inicio = Math.max(0, start.intValue());
                    int fin = Math.min(sequence.length(), end.intValue());
                    if (inicio >= fin) return "";

                    return sequence.substring(inicio, fin);
                });
    }

    @Override
    public Optional<ChromosomeOutDTO> registrarOActualizarSecuencia(Long id, String nuevaSecuencia) {
        return chromosomeRepository.findById(id).map(chromosome -> {
            chromosome.setSequence(nuevaSecuencia);
            return convertirAOutDTO(chromosomeRepository.save(chromosome));
        });
    }

    private ChromosomeOutDTO convertirAOutDTO(Chromosome chromosome) {
        ChromosomeOutDTO dto = new ChromosomeOutDTO();
        dto.setId(chromosome.getId());
        dto.setName(chromosome.getName());
        dto.setLength(chromosome.getLength());

        if (chromosome.getGenome() != null) {
            dto.setGenomeVersion(chromosome.getGenome().getVersionName());
        }

        return dto;
    }


}
