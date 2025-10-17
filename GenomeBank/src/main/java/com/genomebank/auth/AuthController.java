package com.genomebank.auth;

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

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
    public Map<String, Object> login(@RequestBody Map<String, String> request){
        String username = request.get("username");
        String password = request.get("password");

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

        var user = userRepository.findByUsername(username).orElseThrow();
        var roles = user.getRoles().stream().map(Role::getName).toList();
        String token = jwtService.generate(user.getUsername(), roles);

        return Map.of("access_token", token,
                "token_type", "Bearer",
                "roles", roles);
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public Map<String, Object> register(@RequestBody Map<String, String> request){
        if (request.get("username") == null || request.get("password") == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El usuario o la contraseña no puede estar vacio");
        }
        if (userRepository.findByUsername(request.get("username")).isPresent()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El usuario ya existe");
        }

        /*List<String> roleNames = (request.get("roles") == null || request.get("roles").isEmpty())
                ? List.of("USER")
                : request.get("roles");*/

        List<String> roleNames;
        Object rolesObj = request.get("roles");

        if (rolesObj instanceof List<?> rolesList && !rolesList.isEmpty()) {
            roleNames = rolesList.stream()
                    .map(Object::toString)
                    .toList();
        } else {
            roleNames = List.of("USER");
        }


        Set<Role> rolEntities = new HashSet<>();
        for (String roleName : roleNames){
            Role role = roleRepository.findByName(roleName).orElseGet(() -> {
                Role newRole = new Role();
                newRole.setName(roleName);
                return roleRepository.save(newRole);
            });
            rolEntities.add(role);
        }

        User user = new User();
        user.setUsername(request.get("username"));
        user.setPassword(passwordEncoder.encode(request.get("password")));
        user.setRoles(rolEntities);

        userRepository.save(user);

        List<String> roles = rolEntities.stream().map(Role::getName).toList();
        String token = jwtService.generate(user.getUsername(), roles);
        return Map.of("access_token", token,
                "token_type", "Bearer",
                "roles", roles);
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(org.springframework.security.core.AuthenticationException.class)
    public Map<String, String> onAuthError(Exception exception) {
        return Map.of("message", "Credenciales no validas");
    }
}
