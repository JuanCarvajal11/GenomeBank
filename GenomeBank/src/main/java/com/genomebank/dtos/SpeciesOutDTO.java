package com.genomebank.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SpeciesOutDTO {
    private Long id;
    private String scientificName;
    private String commonName;
}
