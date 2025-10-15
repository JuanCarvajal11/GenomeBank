package com.genomebank.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Representa un usuario del sistema GenomeBank.
 * Puede tener uno o varios roles que determinan sus permisos.
 */
@Entity
@Table(name = "users")
@Data
public class User implements UserDetails {
    /** Identificador único autogenerado */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Nombre de usuario único y obligatorio */
    @Column(nullable = false, unique = true, length = 50)
    private String username;

    /** Correo electrónico único del usuario */
    @Column(unique = true, length = 150)
    private String email;

    /** Contraseña encriptada */
    @Column(nullable = false, length = 255)
    private String password;

    /** Indica si el usuario está activo */
    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;

    /** Fecha de creación del usuario */
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    // Relación muchos a muchos con roles, usando carga inmediata (EAGER)
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles", // Nombre de la tabla intermedia en la base de datos
            joinColumns = @JoinColumn(name = "user_id"), // Columna que hace referencia al usuario
            inverseJoinColumns = @JoinColumn(name = "role_id") // Columna que hace referencia al rol
    )
    private Set<Role> roles = new HashSet<>(); // Conjunto de roles asignados al usuario

    //Métodos requeridos por Spring Security
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(r -> (GrantedAuthority) () -> "ROLE_" + r.getName())// Convierte cada rol en un GrantedAuthority
                .collect(Collectors.toSet()); // Devuelve un Set de authorities
    }
    // Devuelve las autoridades (roles) del usuario para Spring Security
    @Override public boolean isAccountNonExpired() { return true; }
    @Override public boolean isAccountNonLocked() { return true; }
    @Override public boolean isCredentialsNonExpired() { return true; }
    @Override public boolean isEnabled() { return isActive; }
}

