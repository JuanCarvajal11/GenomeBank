package com.genomebank.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GeneFunctionOutDTO {
    private Long id;
    private String geneSymbol;
    private String functionCode;
    private String evidence;
}
