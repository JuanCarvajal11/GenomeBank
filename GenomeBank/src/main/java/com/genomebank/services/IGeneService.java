package com.genomebank.services;

import com.genomebank.dtos.GeneInDTO;
import com.genomebank.dtos.GeneOutDTO;

import java.util.List;
import java.util.Optional;

public interface IGeneService {

    /**
     * Listar todos los genes con filtros opcionales.
     * @param chromosomeId filtro opcional por cromosoma
     * @param start filtro opcional por posición inicial
     * @param end filtro opcional por posición final
     * @param symbol filtro opcional por símbolo del gen
     * @return lista de genes que cumplan los filtros
     */
    List<GeneOutDTO> obtenerGenes(Long chromosomeId, Long start, Long end, String symbol);

    /**
     * Consultar un gen específico por su ID.
     * @param id identificador del gen
     * @return el gen si existe
     */
    Optional<GeneOutDTO> obtenerGenPorId(Long id);

    /**
     * Registrar un nuevo gen.
     * @param geneInDTO datos del gen a crear
     * @return el gen creado
     */
    GeneOutDTO crearGen(GeneInDTO geneInDTO);

    /**
     * Actualizar un gen existente.
     * @param id identificador del gen
     * @param geneInDTO datos actualizados del gen
     * @return el gen actualizado si existe
     */
    Optional<GeneOutDTO> actualizarGen(Long id, GeneInDTO geneInDTO);

    /**
     * Eliminar un gen.
     * @param id identificador del gen a eliminar
     */
    void eliminarGen(Long id);

    /**
     * Consultar la secuencia de ADN de un gen.
     * @param id identificador del gen
     * @return la secuencia de ADN del gen si existe
     */
    Optional<String> obtenerSecuenciaGen(Long id);

    /**
     * Registrar o actualizar la secuencia de ADN de un gen.
     * @param id identificador del gen
     * @param sequence nueva secuencia de ADN
     * @return true si se actualizó correctamente, false si el gen no existe
     */
    boolean actualizarSecuenciaGen(Long id, String sequence);
}