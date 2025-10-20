package com.genomebank.services.impl;

import com.genomebank.dtos.FunctionInDTO;
import com.genomebank.dtos.FunctionOutDTO;
import com.genomebank.entities.Function;
import com.genomebank.repositories.FunctionRepository;
import com.genomebank.services.IFunctionService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FunctionService implements IFunctionService {

    private final FunctionRepository functionRepository;

    public FunctionService(FunctionRepository functionRepository) {
        this.functionRepository = functionRepository;
    }

    @Override
    public List<FunctionOutDTO> obtenerFunciones(String code, String category) {
        List<Function> funciones;
        if (code != null && !code.isEmpty()) {
            funciones = functionRepository.findByCode(code);
        } else if (category != null && !category.isEmpty()) {
            funciones = functionRepository.findByCategory(category);
        } else {
            funciones = functionRepository.findAll();
        }

        List<FunctionOutDTO> resultado = new ArrayList<>();
        for (Function f : funciones) {
            resultado.add(convertirAOutDTO(f));
        }
        return resultado;
    }

    @Override
    public Optional<FunctionOutDTO> obtenerFuncionPorId(Long id) {
        Optional<Function> encontrada = functionRepository.findById(id);
        if (encontrada.isPresent()) {
            FunctionOutDTO dto = convertirAOutDTO(encontrada.get());
            return Optional.of(dto);
        }
        return Optional.empty();
    }

    @Override
    public FunctionOutDTO crearFuncion(FunctionInDTO dto) {
        Function f = new Function();
        f.setCode(dto.getCode());
        f.setName(dto.getName());
        f.setCategory(dto.getCategory());
        Function guardada = functionRepository.save(f);
        return convertirAOutDTO(guardada);
    }

    @Override
    public Optional<FunctionOutDTO> actualizarFuncion(Long id, FunctionInDTO dto) {
        Optional<Function> encontrada = functionRepository.findById(id);
        if (encontrada.isPresent()) {
            Function f = encontrada.get();
            f.setCode(dto.getCode());
            f.setName(dto.getName());
            f.setCategory(dto.getCategory());
            Function actualizada = functionRepository.save(f);
            return Optional.of(convertirAOutDTO(actualizada));
        }
        return Optional.empty();
    }

    @Override
    public void eliminarFuncion(Long id) {
        functionRepository.deleteById(id);
    }

    private FunctionOutDTO convertirAOutDTO(Function f) {
        FunctionOutDTO dto = new FunctionOutDTO();
        dto.setId(f.getId());
        dto.setCode(f.getCode());
        dto.setName(f.getName());
        dto.setCategory(f.getCategory());
        return dto;
    }
}
