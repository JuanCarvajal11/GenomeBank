package com.genomebank.services;

import com.genomebank.dtos.SpeciesInDTO;
import com.genomebank.dtos.SpeciesOutDTO;

import java.util.List;
import java.util.Optional;

public interface ISpeciesService {
    List<SpeciesOutDTO> obtenerEspecies();
    Optional<SpeciesOutDTO> obtenerEspeciePorId(Long id);
    SpeciesOutDTO crearEspecie(SpeciesInDTO speciesInDTO);
    Optional<SpeciesOutDTO> actualizarEspecie(Long id, SpeciesInDTO speciesInDTO);
    void eliminarEspecie(Long id);
}
