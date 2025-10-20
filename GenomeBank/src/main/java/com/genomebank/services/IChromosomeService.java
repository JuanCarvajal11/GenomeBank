package com.genomebank.services;

import com.genomebank.dtos.ChromosomeInDTO;
import com.genomebank.dtos.ChromosomeOutDTO;

import java.util.List;
import java.util.Optional;

public interface IChromosomeService {

    // Métodos principales
    List<ChromosomeOutDTO> obtenerCromosomas();

    List<ChromosomeOutDTO> obtenerCromosomasPorGenoma(Long genomeId);

    Optional<ChromosomeOutDTO> obtenerCromosomaPorId(Long id);

    ChromosomeOutDTO crearCromosoma(ChromosomeInDTO chromosomeInDTO);

    Optional<ChromosomeOutDTO> actualizarCromosoma(Long id, ChromosomeInDTO chromosomeInDTO);

    Optional<ChromosomeOutDTO> eliminarCromosoma(Long id);

    // Métodos de gestión de secuencias
    Optional<String> obtenerSecuenciaCompleta(Long id);

    Optional<String> obtenerSubsecuenciaPorRango(Long id, Long start, Long end);

    Optional<ChromosomeOutDTO> registrarOActualizarSecuencia(Long id, String nuevaSecuencia);
}
