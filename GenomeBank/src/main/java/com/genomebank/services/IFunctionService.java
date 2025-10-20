package com.genomebank.services;

import com.genomebank.dtos.FunctionInDTO;
import com.genomebank.dtos.FunctionOutDTO;

import java.util.List;
import java.util.Optional;

public interface IFunctionService {
    List<FunctionOutDTO> obtenerFunciones(String code, String category);
    Optional<FunctionOutDTO> obtenerFuncionPorId(Long id);
    FunctionOutDTO crearFuncion(FunctionInDTO dto);
    Optional<FunctionOutDTO> actualizarFuncion(Long id, FunctionInDTO dto);
    void eliminarFuncion(Long id);
}
