    package com.genomebank.dtos;

    import lombok.AllArgsConstructor;
    import lombok.Data;
    import lombok.NoArgsConstructor;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public class GeneInDTO {
        private String symbol;
        private Long startPosition;
        private Long endPosition;
        private String strand;
        private String sequence;
        private Long chromosomeId;
    }
