package com.genomebank.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {
    private String username;
    private String password;
    private List<String> roles;

    public void validate() {
        List<String> errors = new ArrayList<>();

        if (username == null || username.trim().isEmpty()) {
            errors.add("El nombre de usuario es obligatorio");
        } else if (username.trim().length() < 3) {
            errors.add("El nombre de usuario debe tener al menos 3 caracteres");
        } else if (username.trim().length() > 50) {
            errors.add("El nombre de usuario no puede superar los 50 caracteres");
        }

        if (password == null || password.isEmpty()) {
            errors.add("La contraseña es obligatoria");
        } else if (password.length() < 6) {
            errors.add("La contraseña debe tener al menos 6 caracteres");
        } else if (password.length() > 100) {
            errors.add("La contraseña no puede superar los 100 caracteres");
        }

        if (!errors.isEmpty()) {
            throw new IllegalArgumentException(String.join(", ", errors));
        }
    }

    public String getUsername() {
        return username != null ? username.trim() : null;
    }
}