package com.genomebank.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para crear o actualizar una especie.
 * Contiene solo los datos necesarios desde el cliente.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SpeciesInDTO {
    private String scientificName;
    private String commonName;
}
