package com.genomebank.services.impl;

import com.genomebank.dtos.GeneFunctionInDTO;
import com.genomebank.dtos.GeneFunctionOutDTO;
import com.genomebank.entities.Function;
import com.genomebank.entities.Gene;
import com.genomebank.entities.GeneFunction;
import com.genomebank.repositories.FunctionRepository;
import com.genomebank.repositories.GeneFunctionRepository;
import com.genomebank.repositories.GeneRepository;
import com.genomebank.services.IGeneFunctionService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GeneFunctionService implements IGeneFunctionService {

    private final GeneFunctionRepository geneFunctionRepository;
    private final GeneRepository geneRepository;
    private final FunctionRepository functionRepository;

    public GeneFunctionService(GeneFunctionRepository geneFunctionRepository,
                               GeneRepository geneRepository,
                               FunctionRepository functionRepository) {
        this.geneFunctionRepository = geneFunctionRepository;
        this.geneRepository = geneRepository;
        this.functionRepository = functionRepository;
    }

    @Override
    public List<GeneFunctionOutDTO> obtenerFuncionesPorGen(Long geneId) {
        return geneFunctionRepository.findAll().stream()
                .filter(gf -> gf.getGene().getId().equals(geneId))
                .map(this::convertirAOutDTO)
                .collect(Collectors.toList());
    }

    @Override
    public GeneFunctionOutDTO asociarFuncionAGen(Long geneId, Long functionId, GeneFunctionInDTO inDTO) {
        Gene gene = geneRepository.findById(geneId)
                .orElseThrow(() -> new RuntimeException("Gen no encontrado con ID: " + geneId));
        Function function = functionRepository.findById(functionId)
                .orElseThrow(() -> new RuntimeException("Función no encontrada con ID: " + functionId));

        GeneFunction gf = new GeneFunction();
        gf.setGene(gene);
        gf.setFunction(function);
        gf.setEvidence(inDTO.getEvidence());

        GeneFunction guardado = geneFunctionRepository.save(gf);
        return convertirAOutDTO(guardado);
    }

    @Override
    public Optional<GeneFunctionOutDTO> eliminarRelacion(Long geneId, Long functionId) {
        return geneFunctionRepository.findAll().stream()
                .filter(gf -> gf.getGene().getId().equals(geneId)
                        && gf.getFunction().getId().equals(functionId))
                .findFirst()
                .map(gf -> {
                    GeneFunctionOutDTO dto = convertirAOutDTO(gf);
                    geneFunctionRepository.delete(gf);
                    return dto;
                });
    }

    private GeneFunctionOutDTO convertirAOutDTO(GeneFunction gf) {
        GeneFunctionOutDTO dto = new GeneFunctionOutDTO();
        dto.setId(gf.getId());
        dto.setGeneSymbol(gf.getGene().getSymbol());
        dto.setFunctionCode(gf.getFunction().getCode());
        dto.setEvidence(gf.getEvidence());
        return dto;
    }
}
