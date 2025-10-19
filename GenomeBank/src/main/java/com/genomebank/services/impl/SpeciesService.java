package com.genomebank.services.impl;

import com.genomebank.dtos.SpeciesInDTO;
import com.genomebank.dtos.SpeciesOutDTO;
import com.genomebank.entities.Species;
import com.genomebank.repositories.SpeciesRepository;
import com.genomebank.services.ISpeciesService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class SpeciesService implements ISpeciesService {
    private final SpeciesRepository speciesRepository;


    public SpeciesService(SpeciesRepository speciesRepository) {
        this.speciesRepository = speciesRepository;
    }

    @Override
    public List<SpeciesOutDTO> obtenerEspecies() {
        return speciesRepository.findAll().stream()
                .map(species -> {
                    SpeciesOutDTO oe = new SpeciesOutDTO();
                    oe.setId(species.getId());
                    oe.setScientificName(species.getScientificName());
                    oe.setCommonName(species.getCommonName());
                    return oe;
                })
                .collect(Collectors.toList());
    }

    @Override
    public Optional<SpeciesOutDTO> obtenerEspeciesPorId(Long id) {
        return speciesRepository.findById(id).map(species -> {
            SpeciesOutDTO oep = new SpeciesOutDTO();
            oep.setId(species.getId());
            oep.setScientificName(species.getScientificName());
            oep.setCommonName(species.getCommonName());
            return oep;
        });
    }

    @Override
    public SpeciesOutDTO crearEspecie(SpeciesInDTO speciesInDTO) {
        Species specie = new Species();
        specie.setScientificName(speciesInDTO.getScientificName());
        specie.setCommonName(speciesInDTO.getCommonName());
        Species savedSpecies = speciesRepository.save(specie);
        
        SpeciesOutDTO ce = new SpeciesOutDTO();
        ce.setId(savedSpecies.getId());
        ce.setScientificName(savedSpecies.getScientificName());
        ce.setCommonName(savedSpecies.getCommonName());
        return ce;
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