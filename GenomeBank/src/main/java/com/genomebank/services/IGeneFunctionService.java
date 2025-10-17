package com.genomebank.services;

import com.genomebank.entities.GeneFunction;

import java.util.List;
import java.util.Optional;

public interface IGeneFunctionService {
    public List<GeneFunction> obtenerRelaciones();

    public Optional<GeneFunction> obtenerRelacionPorId(Long id);

    public GeneFunction crearRelacion(GeneFunction relation);

    public Optional<GeneFunction> actualizarRelacion(Long id, GeneFunction relation);

    public void eliminarRelacion(Long id);
}
