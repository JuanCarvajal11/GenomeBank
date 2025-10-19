package com.genomebank.services;

import com.genomebank.dtos.GeneFunctionInDTO;
import com.genomebank.dtos.GeneFunctionOutDTO;

import java.util.List;
import java.util.Optional;

public interface IGeneService {

    List<GeneFunctionOutDTO> obtenerGenes();

    Optional<GeneFunctionOutDTO> obtenerGenPorId(Long id);

    GeneFunctionOutDTO crearGen(GeneFunctionInDTO geneInDTO);

    Optional<GeneFunctionOutDTO> actualizarGen(Long id, GeneFunctionInDTO geneInDTO);

    void eliminarGen(Long id);
}
