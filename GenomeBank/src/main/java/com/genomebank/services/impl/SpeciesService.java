package com.genomebank.services.impl;

import com.genomebank.entities.Species;
import com.genomebank.repositories.SpeciesRepository;
import com.genomebank.services.ISpeciesService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class SpeciesService implements ISpeciesService {
    private final SpeciesRepository speciesRepository;


    public SpeciesService(SpeciesRepository speciesRepository) {
        this.speciesRepository = speciesRepository;
    }

    @Override
    public List<Species> obtenerEspecies() {
        return this.speciesRepository.findAll();
    }

    @Override
    public Optional<Species> obtenerEspeciesPorId(Long id){return this.speciesRepository.findById(id);}

    @Override
    public Species crearEspecie(Species species){
        Species specie = new Species();
        specie.setScientificName(species.getScientificName());
        specie.setCommonName(species.getCommonName());

        return speciesRepository.save(specie);

    }

    @Override
    public Optional<Species> actualizarEspecie(Long id, Species species){
        return this.speciesRepository.findById(id).map(especieEncontrada -> {
            especieEncontrada.setCommonName(species.getCommonName());
            especieEncontrada.setScientificName(species.getScientificName());
            return speciesRepository.save(especieEncontrada);
        });
    }

    @Override
    public void eliminarEspecie(Long id) {
        speciesRepository.deleteById(id);
    }

}