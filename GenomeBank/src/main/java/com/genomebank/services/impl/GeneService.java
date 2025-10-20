package com.genomebank.services.impl;

import com.genomebank.dtos.GeneInDTO;
import com.genomebank.dtos.GeneOutDTO;
import com.genomebank.entities.Chromosome;
import com.genomebank.entities.Gene;
import com.genomebank.repositories.ChromosomeRepository;
import com.genomebank.repositories.GeneRepository;
import com.genomebank.services.IGeneService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class GeneService implements IGeneService {

    private final GeneRepository geneRepository;
    private final ChromosomeRepository chromosomeRepository;

    public GeneService(GeneRepository geneRepository,
                       ChromosomeRepository chromosomeRepository) {
        this.geneRepository = geneRepository;
        this.chromosomeRepository = chromosomeRepository;
    }

    @Override
    public List<GeneOutDTO> obtenerGenes(Long chromosomeId, Long start, Long end, String symbol) {
        List<Gene> genes;

        // Aplicar filtros según los parámetros recibidos
        if (chromosomeId != null && start != null && end != null) {
            // Filtro por cromosoma y rango de posición
            genes = geneRepository.findByChromosomeIdAndStartPositionGreaterThanEqualAndEndPositionLessThanEqual(
                    chromosomeId, start, end);
        } else if (chromosomeId != null) {
            // Filtro solo por cromosoma
            genes = geneRepository.findByChromosomeId(chromosomeId);
        } else if (symbol != null && !symbol.isEmpty()) {
            // Filtro por símbolo
            genes = geneRepository.findBySymbolContainingIgnoreCase(symbol);
        } else {
            // Sin filtros, devolver todos
            genes = geneRepository.findAll();
        }

        // Convertir a DTO usando bucle tradicional
        List<GeneOutDTO> resultado = new ArrayList<>();
        for (Gene gene : genes) {
            resultado.add(convertirAOutDTO(gene));
        }

        return resultado;
    }

    @Override
    public Optional<GeneOutDTO> obtenerGenPorId(Long id) {
        Optional<Gene> geneOpt = geneRepository.findById(id);

        if (geneOpt.isPresent()) {
            return Optional.of(convertirAOutDTO(geneOpt.get()));
        }

        return Optional.empty();
    }

    @Override
    public GeneOutDTO crearGen(GeneInDTO geneInDTO) {
        Optional<Chromosome> chromosomeOpt = chromosomeRepository.findById(geneInDTO.getChromosomeId());

        if (!chromosomeOpt.isPresent()) {
            throw new RuntimeException("Chromosome no encontrado con ID: " + geneInDTO.getChromosomeId());
        }

        Chromosome chromosome = chromosomeOpt.get();

        Gene gene = new Gene();
        gene.setSymbol(geneInDTO.getSymbol());
        gene.setStartPosition(geneInDTO.getStartPosition());
        gene.setEndPosition(geneInDTO.getEndPosition());

        // Convertir String a Character para strand
        if (geneInDTO.getStrand() != null && !geneInDTO.getStrand().isEmpty()) {
            gene.setStrand(geneInDTO.getStrand().charAt(0));
        }

        gene.setSequence(geneInDTO.getSequence());
        gene.setChromosome(chromosome);

        Gene savedGene = geneRepository.save(gene);
        return convertirAOutDTO(savedGene);
    }

    @Override
    public Optional<GeneOutDTO> actualizarGen(Long id, GeneInDTO geneInDTO) {
        Optional<Gene> geneOpt = geneRepository.findById(id);

        if (!geneOpt.isPresent()) {
            return Optional.empty();
        }

        Gene gene = geneOpt.get();

        Optional<Chromosome> chromosomeOpt = chromosomeRepository.findById(geneInDTO.getChromosomeId());

        if (!chromosomeOpt.isPresent()) {
            throw new RuntimeException("Chromosome no encontrado con ID: " + geneInDTO.getChromosomeId());
        }

        Chromosome chromosome = chromosomeOpt.get();

        gene.setSymbol(geneInDTO.getSymbol());
        gene.setStartPosition(geneInDTO.getStartPosition());
        gene.setEndPosition(geneInDTO.getEndPosition());

        // Convertir String a Character para strand
        if (geneInDTO.getStrand() != null && !geneInDTO.getStrand().isEmpty()) {
            gene.setStrand(geneInDTO.getStrand().charAt(0));
        }

        gene.setSequence(geneInDTO.getSequence());
        gene.setChromosome(chromosome);

        Gene updatedGene = geneRepository.save(gene);
        return Optional.of(convertirAOutDTO(updatedGene));
    }

    @Override
    public void eliminarGen(Long id) {
        geneRepository.deleteById(id);
    }

    @Override
    public Optional<String> obtenerSecuenciaGen(Long id) {
        Optional<Gene> geneOpt = geneRepository.findById(id);

        if (geneOpt.isPresent()) {
            return Optional.of(geneOpt.get().getSequence());
        }

        return Optional.empty();
    }

    @Override
    public boolean actualizarSecuenciaGen(Long id, String sequence) {
        Optional<Gene> geneOpt = geneRepository.findById(id);

        if (geneOpt.isPresent()) {
            Gene gene = geneOpt.get();
            gene.setSequence(sequence);
            geneRepository.save(gene);
            return true;
        }

        return false;
    }

    /**
     * Convierte una entidad Gene a un DTO de salida.
     */
    private GeneOutDTO convertirAOutDTO(Gene gene) {
        GeneOutDTO outDTO = new GeneOutDTO();
        outDTO.setId(gene.getId());
        outDTO.setSymbol(gene.getSymbol());
        outDTO.setStartPosition(gene.getStartPosition());
        outDTO.setEndPosition(gene.getEndPosition());

        // Convertir Character a String para strand
        if (gene.getStrand() != null) {
            outDTO.setStrand(String.valueOf(gene.getStrand()));
        }

        if (gene.getChromosome() != null) {
            outDTO.setChromosomeName(gene.getChromosome().getName());
        }

        return outDTO;
    }
}