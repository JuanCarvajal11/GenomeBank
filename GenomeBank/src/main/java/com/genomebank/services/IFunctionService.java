package com.genomebank.services;

import com.genomebank.dtos.FunctionInDTO;
import com.genomebank.dtos.FunctionOutDTO;
import java.util.List;
import java.util.Optional;

public interface IFunctionService {
    List<FunctionOutDTO> obtenerFunciones();

    Optional<FunctionOutDTO> obtenerFuncionPorId(Long id);

    FunctionOutDTO crearFuncion(FunctionInDTO functionInDTO);

    Optional<FunctionOutDTO> actualizarFuncion(Long id, FunctionInDTO functionInDTO);

    void eliminarFuncion(Long id);
}
