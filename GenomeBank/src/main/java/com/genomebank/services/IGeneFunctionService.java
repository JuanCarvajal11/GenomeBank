package com.genomebank.services;

import com.genomebank.dtos.GeneFunctionInDTO;
import com.genomebank.dtos.GeneFunctionOutDTO;
import com.genomebank.entities.GeneFunction;

import java.util.List;
import java.util.Optional;

public interface IGeneFunctionService {
    public List<GeneFunction> obtenerRelaciones();

    public Optional<GeneFunctionOutDTO> obtenerRelacionPorId(Long id);

    public GeneFunction crearRelacion(GeneFunctionInDTO geneFunctionInDTO);

    public Optional<GeneFunction> actualizarRelacion(Long id, GeneFunction relation);

    public Optional<GeneFunctionOutDTO> eliminarRelacion(Long id);
}
