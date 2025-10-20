package com.genomebank.services;

import com.genomebank.dtos.GenomeInDTO;
import com.genomebank.dtos.GenomeOutDTO;
import com.genomebank.dtos.SpeciesOutDTO;

import java.util.List;
import java.util.Optional;

public interface  IGenomeService {
    public  List<GenomeOutDTO> obtenerGenomas();

    public List<GenomeOutDTO> obtenerGenomasPorEspecie(Long speciesId);

    public  Optional<GenomeOutDTO> obtenerGenomaPorId(Long id);

    public  GenomeOutDTO crearGenoma(GenomeInDTO genomeInDTO);

    public  Optional<GenomeOutDTO> actualizarGenoma(Long id, GenomeInDTO genomeInDTO);

    public  Optional<GenomeOutDTO> eliminarGenoma(Long id);
}
