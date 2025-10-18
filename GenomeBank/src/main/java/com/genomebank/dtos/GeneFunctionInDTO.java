package com.genomebank.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GeneFunctionInDTO {
    private Long geneId;
    private Long functionId;
    private String evidence;
}
