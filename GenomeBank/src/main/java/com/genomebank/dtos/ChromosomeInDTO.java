package com.genomebank.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChromosomeInDTO {
    private String name;
    private Long length;
    private String sequence;
    private Long genomeId;
}
