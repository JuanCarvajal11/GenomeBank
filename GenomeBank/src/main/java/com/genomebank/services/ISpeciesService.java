package com.genomebank.services;

import com.genomebank.entities.Species;

import java.util.List;
import java.util.Optional;

public interface ISpeciesService {
    public List<Species> obtenerEspecies();
    public Optional<Species> obtenerEspeciesPorId(Long id);
    public Species crearEspecie(Species species);
    public Optional<Species> actualizarEspecie(Long id, Species species);

    void eliminarEspecie(Long id);
}
