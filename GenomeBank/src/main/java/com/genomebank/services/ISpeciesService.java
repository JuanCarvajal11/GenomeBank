package com.genomebank.services;

import com.genomebank.dtos.SpeciesInDTO;
import com.genomebank.dtos.SpeciesOutDTO;
import com.genomebank.entities.Species;

import java.util.List;
import java.util.Optional;

public interface ISpeciesService {
    public List<SpeciesOutDTO> obtenerEspecies();
    public Optional<SpeciesOutDTO> obtenerEspeciesPorId(Long id);
    public SpeciesOutDTO crearEspecie(SpeciesInDTO speciesInDTO);
    public Optional<Species> actualizarEspecie(Long id, Species species);

    void eliminarEspecie(Long id);
}
