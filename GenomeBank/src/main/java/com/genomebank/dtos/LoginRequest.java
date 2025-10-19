package com.genomebank.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {
    private String username;
    private String password;

    public void validate() {
        List<String> errors = new ArrayList<>();

        if (username == null || username.trim().isEmpty()) {
            errors.add("El nombre de usuario es obligatorio");
        }

        if (password == null || password.isEmpty()) {
            errors.add("La contraseña es obligatoria");
        }

        if (!errors.isEmpty()) {
            throw new IllegalArgumentException(String.join(", ", errors));
        }
    }
}