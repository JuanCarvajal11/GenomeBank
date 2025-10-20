package com.genomebank.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GeneOutDTO {
    private Long id;
    private String symbol;
    private Long startPosition;
    private Long endPosition;
    private String strand;
    private String chromosomeName;
}
