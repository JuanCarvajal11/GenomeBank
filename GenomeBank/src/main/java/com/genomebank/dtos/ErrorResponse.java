package com.genomebank.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {
    private String message;
    private int status;
    private List<String> errors;
    private Long timestamp;

    public static ErrorResponse of(String message, int status) {
        return ErrorResponse.builder()
                .message(message)
                .status(status)
                .timestamp(System.currentTimeMillis())
                .build();
    }

    public static ErrorResponse withErrors(String message, int status, List<String> errors) {
        return ErrorResponse.builder()
                .message(message)
                .status(status)
                .errors(errors)
                .timestamp(System.currentTimeMillis())
                .build();
    }
}