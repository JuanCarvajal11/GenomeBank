package com.genomebank.services.impl;

import com.genomebank.dtos.GeneFunctionInDTO;
import com.genomebank.dtos.GeneFunctionOutDTO;
import com.genomebank.entities.Chromosome;
import com.genomebank.entities.Function;
import com.genomebank.entities.Gene;
import com.genomebank.repositories.ChromosomeRepository;
import com.genomebank.repositories.FunctionRepository;
import com.genomebank.repositories.GeneRepository;
import com.genomebank.services.IGeneService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GeneService implements IGeneService {

    private final GeneRepository geneRepository;
    private final ChromosomeRepository chromosomeRepository;
    private final FunctionRepository functionRepository;

    public GeneService(GeneRepository geneRepository,
                       ChromosomeRepository chromosomeRepository,
                       FunctionRepository functionRepository) {
        this.geneRepository = geneRepository;
        this.chromosomeRepository = chromosomeRepository;
        this.functionRepository = functionRepository;
    }

    @Override
    public List<GeneFunctionOutDTO> obtenerGenes() {
        return geneRepository.findAll().stream()
                .map(this::convertirAOutDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<GeneFunctionOutDTO> obtenerGenPorId(Long id) {
        return geneRepository.findById(id)
                .map(this::convertirAOutDTO);
    }

    @Override
    public GeneFunctionOutDTO crearGen(GeneFunctionInDTO geneInDTO) {
        Chromosome chromosome = chromosomeRepository.getReferenceById(geneInDTO.getGeneId());
        Function function = functionRepository.getReferenceById(geneInDTO.getFunctionId());

        Gene gene = new Gene();
        gene.setSymbol("GENE_" + geneInDTO.getGeneId());
        gene.setStartPosition(0L);
        gene.setEndPosition(100L);
        gene.setStrand('+');
        gene.setSequence("N/A");
        gene.setChromosome(chromosome);

        Gene saved = geneRepository.save(gene);
        return convertirAOutDTO(saved);
    }

    @Override
    public Optional<GeneFunctionOutDTO> actualizarGen(Long id, GeneFunctionInDTO geneInDTO) {
        return geneRepository.findById(id).map(g -> {
            Function function = functionRepository.getReferenceById(geneInDTO.getFunctionId());
            g.setSymbol("GENE_" + geneInDTO.getGeneId());
            g.setSequence("Actualizado");
            return convertirAOutDTO(geneRepository.save(g));
        });
    }

    @Override
    public void eliminarGen(Long id) {
        geneRepository.deleteById(id);
    }

    private GeneFunctionOutDTO convertirAOutDTO(Gene gene) {
        GeneFunctionOutDTO dto = new GeneFunctionOutDTO();
        dto.setId(gene.getId());
        dto.setGeneSymbol(gene.getSymbol());
        dto.setFunctionCode(gene.getChromosome() != null ? gene.getChromosome().getName() : "N/A");
        dto.setEvidence("Experimental");
        return dto;
    }
}
