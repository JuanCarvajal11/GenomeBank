package com.genomebank.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChromosomeOutDTO {
    private Long id;
    private String name;
    private Long length;
    private String genomeVersion;
}
