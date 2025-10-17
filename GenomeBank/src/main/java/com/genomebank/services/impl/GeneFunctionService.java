package com.genomebank.services.impl;

import com.genomebank.entities.GeneFunction;
import com.genomebank.repositories.GeneFunctionRepository;
import com.genomebank.services.IGeneFunctionService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GeneFunctionService implements IGeneFunctionService {

    private final GeneFunctionRepository geneFunctionRepository;

    public GeneFunctionService(GeneFunctionRepository geneFunctionRepository) {
        this.geneFunctionRepository = geneFunctionRepository;
    }

    @Override
    public List<GeneFunction> obtenerRelaciones() {
        return geneFunctionRepository.findAll();
    }

    @Override
    public Optional<GeneFunction> obtenerRelacionPorId(Long id) {
        return geneFunctionRepository.findById(id);
    }

    @Override
    public GeneFunction crearRelacion(GeneFunction relation) {
        GeneFunction gf = new GeneFunction();
        gf.setGene(relation.getGene());
        gf.setFunction(relation.getFunction());
        gf.setEvidence(relation.getEvidence());
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
    public void eliminarRelacion(Long id) {
        geneFunctionRepository.deleteById(id);
    }
}
