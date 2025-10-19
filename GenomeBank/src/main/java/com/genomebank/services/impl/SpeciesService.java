package com.genomebank.services.impl;

import com.genomebank.dtos.SpeciesInDTO;
import com.genomebank.dtos.SpeciesOutDTO;
import com.genomebank.entities.Species;
import com.genomebank.repositories.SpeciesRepository;
import com.genomebank.services.ISpeciesService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Implementación del servicio para gestionar especies biológicas.
 * Maneja la lógica de negocio y conversión entre entidades y DTOs.
 */
@Service
public class SpeciesService implements ISpeciesService {

    private final SpeciesRepository speciesRepository;

    public SpeciesService(SpeciesRepository speciesRepository) {
        this.speciesRepository = speciesRepository;
    }

    @Override
    public List<SpeciesOutDTO> obtenerEspecies() {
        List<Species> especies = speciesRepository.findAll();
        List<SpeciesOutDTO> resultado = new ArrayList<>();

        for (Species species : especies) {
            SpeciesOutDTO dto = new SpeciesOutDTO();
            dto.setId(species.getId());
            dto.setScientificName(species.getScientificName());
            dto.setCommonName(species.getCommonName());
            resultado.add(dto);
        }

        return resultado;
    }

    @Override
    public Optional<SpeciesOutDTO> obtenerEspeciePorId(Long id) {
        Optional<Species> speciesOpt = speciesRepository.findById(id);

        if (speciesOpt.isPresent()) {
            Species species = speciesOpt.get();
            SpeciesOutDTO dto = new SpeciesOutDTO();
            dto.setId(species.getId());
            dto.setScientificName(species.getScientificName());
            dto.setCommonName(species.getCommonName());
            return Optional.of(dto);
        }

        return Optional.empty();
    }

    @Override
    public SpeciesOutDTO crearEspecie(SpeciesInDTO speciesInDTO) {
        Species species = new Species();
        species.setScientificName(speciesInDTO.getScientificName());
        species.setCommonName(speciesInDTO.getCommonName());

        Species savedSpecies = speciesRepository.save(species);

        SpeciesOutDTO dto = new SpeciesOutDTO();
        dto.setId(savedSpecies.getId());
        dto.setScientificName(savedSpecies.getScientificName());
        dto.setCommonName(savedSpecies.getCommonName());

        return dto;
    }

    @Override
    public Optional<SpeciesOutDTO> actualizarEspecie(Long id, SpeciesInDTO speciesInDTO) {
        Optional<Species> speciesOpt = speciesRepository.findById(id);

        if (speciesOpt.isPresent()) {
            Species especieEncontrada = speciesOpt.get();
            especieEncontrada.setScientificName(speciesInDTO.getScientificName());
            especieEncontrada.setCommonName(speciesInDTO.getCommonName());

            Species savedSpecies = speciesRepository.save(especieEncontrada);

            SpeciesOutDTO dto = new SpeciesOutDTO();
            dto.setId(savedSpecies.getId());
            dto.setScientificName(savedSpecies.getScientificName());
            dto.setCommonName(savedSpecies.getCommonName());

            return Optional.of(dto);
        }

        return Optional.empty();
    }

    @Override
    public void eliminarEspecie(Long id) {
        speciesRepository.deleteById(id);
    }

}