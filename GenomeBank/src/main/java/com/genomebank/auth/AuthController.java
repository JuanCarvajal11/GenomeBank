package com.genomebank.auth;

import com.genomebank.dtos.ErrorResponse;
import com.genomebank.dtos.AuthResponse;
import com.genomebank.dtos.LoginRequest;
import com.genomebank.dtos.RegisterRequest;
import com.genomebank.entities.Role;
import com.genomebank.entities.User;
import com.genomebank.repositories.RoleRepository;
import com.genomebank.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final RoleRepository roleRepository;

    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequest request) {
        try {
            request.validate();

            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );

            User user = userRepository.findByUsername(request.getUsername()).orElseThrow();

            List<String> roles = new ArrayList<>();
            for (Role role : user.getRoles()) {
                roles.add(role.getName());
            }

            String token = jwtService.generate(user.getUsername(), roles);

            return AuthResponse.fromUserData(user.getUsername(), token, roles);

        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public AuthResponse register(@RequestBody RegisterRequest request) {
        try {
            request.validate();

            if (userRepository.findByUsername(request.getUsername()).isPresent()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El usuario ya existe");
            }

            List<String> roleNames = (request.getRoles() == null || request.getRoles().isEmpty())
                    ? List.of("USER")
                    : request.getRoles();

            Set<Role> rolEntities = new HashSet<>();
            for (String roleName : roleNames) {
                Role role = roleRepository.findByName(roleName).orElseGet(() -> {
                    Role newRole = new Role();
                    newRole.setName(roleName);
                    return roleRepository.save(newRole);
                });
                rolEntities.add(role);
            }

            User user = new User();
            user.setUsername(request.getUsername());
            user.setPassword(passwordEncoder.encode(request.getPassword()));
            user.setRoles(rolEntities);

            userRepository.save(user);

            List<String> roles = new ArrayList<>();
            for (Role role : rolEntities) {
                roles.add(role.getName());
            }

            String token = jwtService.generate(user.getUsername(), roles);

            return AuthResponse.fromUserData(user.getUsername(), token, roles);

        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(org.springframework.security.core.AuthenticationException.class)
    public ErrorResponse onAuthError(Exception exception) {
        return ErrorResponse.of("Credenciales no válidas", HttpStatus.UNAUTHORIZED.value());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ResponseStatusException.class)
    public ErrorResponse onBadRequest(ResponseStatusException exception) {
        return ErrorResponse.of(exception.getReason(), exception.getStatusCode().value());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ErrorResponse onGeneralError(Exception exception) {
        return ErrorResponse.of("Error interno del servidor", HttpStatus.INTERNAL_SERVER_ERROR.value());
    }
}
