package com.genomebank.services;

import com.genomebank.dtos.ChromosomeInDTO;
import com.genomebank.dtos.ChromosomeOutDTO;

import java.util.List;
import java.util.Optional;

public interface IChromosomeService {
    ChromosomeOutDTO crearCromosoma(ChromosomeInDTO chromosomeInDTO);

    List<ChromosomeOutDTO> obtenerCromosomas();

    Optional<ChromosomeOutDTO> obtenerCromosomaPorId(Long id);

    Optional<ChromosomeOutDTO> actualizarCromosoma(Long id, ChromosomeInDTO chromosomeInDTO);

    void eliminarCromosoma(Long id);
}
