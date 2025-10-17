package com.genomebank.services;

import com.genomebank.entities.Genome;

import java.util.List;
import java.util.Optional;

public interface  IGenomeService {
    public  List<Genome> obtenerGenomas();

    public  Optional<Genome> obtenerGenomaPorId(Long id);

    public  Genome crearGenoma(Genome genome);

    public  Optional<Genome> actualizarGenoma(Long id, Genome genome);

    public  void eliminarGenoma(Long id);
}
