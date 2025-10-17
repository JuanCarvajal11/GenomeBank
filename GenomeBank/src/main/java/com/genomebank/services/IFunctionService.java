package com.genomebank.services;

import com.genomebank.entities.Function;

import java.util.List;
import java.util.Optional;

public interface IFunctionService {
    public List<Function> obtenerFunciones();

    public Optional<Function> obtenerFuncionPorId(Long id);

    public Function crearFuncion(Function function);

    public Optional<Function> actualizarFuncion(Long id, Function function);

    public void eliminarFuncion(Long id);
}
