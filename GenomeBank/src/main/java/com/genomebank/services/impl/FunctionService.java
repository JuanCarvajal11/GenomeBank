package com.genomebank.services.impl;

import com.genomebank.entities.Function;
import com.genomebank.repositories.FunctionRepository;
import com.genomebank.services.IFunctionService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FunctionService implements IFunctionService {

    private final FunctionRepository functionRepository;

    public FunctionService(FunctionRepository functionRepository) {
        this.functionRepository = functionRepository;
    }

    @Override
    public List<Function> obtenerFunciones() {
        return functionRepository.findAll();
    }

    @Override
    public Optional<Function> obtenerFuncionPorId(Long id) {
        return functionRepository.findById(id);
    }

    @Override
    public Function crearFuncion(Function function) {
        Function f = new Function();
        f.setCode(function.getCode());
        f.setName(function.getName());
        f.setCategory(function.getCategory());
        return functionRepository.save(f);
    }

    @Override
    public Optional<Function> actualizarFuncion(Long id, Function function) {
        return functionRepository.findById(id).map(fEncontrada -> {
            fEncontrada.setCode(function.getCode());
            fEncontrada.setName(function.getName());
            fEncontrada.setCategory(function.getCategory());
            return functionRepository.save(fEncontrada);
        });
    }

    @Override
    public void eliminarFuncion(Long id) {
        functionRepository.deleteById(id);
    }
}
