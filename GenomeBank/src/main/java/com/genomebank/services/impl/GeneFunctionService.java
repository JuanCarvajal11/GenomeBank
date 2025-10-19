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
    public List<GeneFunction> obtenerRelaciones() {
        return geneFunctionRepository.findAll();
    }

    @Override
    public Optional<GeneFunctionOutDTO> obtenerRelacionPorId(Long id) {
        return geneFunctionRepository.findById(id).map(geneFunction -> {
            GeneFunctionOutDTO orp = new GeneFunctionOutDTO();
            orp.setId(geneFunction.getId());
            orp.setGeneSymbol(geneFunction.getGene().getSymbol());
            orp.setFunctionCode(geneFunction.getFunction().getCode());
            orp.setEvidence(geneFunction.getEvidence());
            return orp;
        });
    }

    @Override
    public GeneFunction crearRelacion(GeneFunctionInDTO geneFunctionInDTO) {
        Gene gene = geneRepository.getReferenceById(geneFunctionInDTO.getGeneId());
        Function function = functionRepository.getReferenceById(geneFunctionInDTO.getFunctionId());
        GeneFunction gf = new GeneFunction();
        gf.setGene(gene);
        gf.setFunction(function);
        gf.setEvidence(geneFunctionInDTO.getEvidence());
        return geneFunctionRepository.save(gf);
    }

    @Override
    public Optional<GeneFunction> actualizarRelacion(Long id, GeneFunction relation) {
        return geneFunctionRepository.findById(id).map(relEncontrada -> {
            relEncontrada.setGene(relation.getGene());
            relEncontrada.setFunction(relation.getFunction());
            relEncontrada.setEvidence(relation.getEvidence());
            return geneFunctionRepository.save(relEncontrada);
        });
    }

    @Override
    public Optional<GeneFunctionOutDTO> eliminarRelacion(Long id) {
        return geneFunctionRepository.findById(id).map(geneFunction -> {
            GeneFunctionOutDTO er = new GeneFunctionOutDTO();
            er.setId(geneFunction.getId());
            er.setGeneSymbol(geneFunction.getGene().getSymbol());
            er.setFunctionCode(geneFunction.getFunction().getCode());
            er.setEvidence(geneFunction.getEvidence());
            geneFunctionRepository.deleteById(id);
            return er;
        });
    }
}
