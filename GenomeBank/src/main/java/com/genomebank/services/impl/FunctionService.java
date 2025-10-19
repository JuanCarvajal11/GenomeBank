package com.genomebank.services.impl;

import com.genomebank.dtos.FunctionInDTO;
import com.genomebank.dtos.FunctionOutDTO;
import com.genomebank.entities.Function;
import com.genomebank.repositories.FunctionRepository;
import com.genomebank.services.IFunctionService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FunctionService implements IFunctionService {

    private final FunctionRepository functionRepository;

    public FunctionService(FunctionRepository functionRepository) {
        this.functionRepository = functionRepository;
    }

    @Override
    public List<FunctionOutDTO> obtenerFunciones() {
        return functionRepository.findAll().stream()
                .map(this::convertirAOutDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<FunctionOutDTO> obtenerFuncionPorId(Long id) {
        return functionRepository.findById(id).map(this::convertirAOutDTO);
    }

    @Override
    public FunctionOutDTO crearFuncion(FunctionInDTO functionInDTO) {
        Function f = new Function();
        f.setCode(functionInDTO.getCode());
        f.setName(functionInDTO.getName());
        f.setCategory(functionInDTO.getCategory());
        return convertirAOutDTO(functionRepository.save(f));
    }

    @Override
    public Optional<FunctionOutDTO> actualizarFuncion(Long id, FunctionInDTO functionInDTO) {
        return functionRepository.findById(id).map(fEncontrada -> {
            fEncontrada.setCode(functionInDTO.getCode());
            fEncontrada.setName(functionInDTO.getName());
            fEncontrada.setCategory(functionInDTO.getCategory());
            return convertirAOutDTO(functionRepository.save(fEncontrada));
        });
    }

    @Override
    public void eliminarFuncion(Long id) {
        functionRepository.deleteById(id);
    }

    private FunctionOutDTO convertirAOutDTO(Function function) {
        FunctionOutDTO dto = new FunctionOutDTO();
        dto.setId(function.getId());
        dto.setCode(function.getCode());
        dto.setName(function.getName());
        dto.setCategory(function.getCategory());
        return dto;
    }
}
