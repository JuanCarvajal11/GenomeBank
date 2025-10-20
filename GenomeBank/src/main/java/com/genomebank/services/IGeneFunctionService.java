package com.genomebank.services;

import com.genomebank.dtos.GeneFunctionInDTO;
import com.genomebank.dtos.GeneFunctionOutDTO;
import java.util.List;
import java.util.Optional;

public interface IGeneFunctionService {

    // Obtener todas las funciones asociadas a un gen
    List<GeneFunctionOutDTO> obtenerFuncionesPorGen(Long geneId);

    // Asociar una función a un gen
    GeneFunctionOutDTO asociarFuncionAGen(Long geneId, Long functionId, GeneFunctionInDTO inDTO);

    // Eliminar una asociación gen-función
    Optional<GeneFunctionOutDTO> eliminarRelacion(Long geneId, Long functionId);
}
