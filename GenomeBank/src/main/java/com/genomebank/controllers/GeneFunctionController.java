package com.genomebank.controllers;

import com.genomebank.dtos.GeneFunctionInDTO;
import com.genomebank.dtos.GeneFunctionOutDTO;
import com.genomebank.services.IGeneFunctionService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/genes")
public class GeneFunctionController {

    private final IGeneFunctionService geneFunctionService;

    public GeneFunctionController(IGeneFunctionService geneFunctionService) {
        this.geneFunctionService = geneFunctionService;
    }

    /**
     * GET /genes/{id}/functions → Listar funciones asociadas a un gen.
     * Acceso: ADMIN y USER
     */
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("/{id}/functions")
    public ResponseEntity<List<GeneFunctionOutDTO>> listarFuncionesPorGen(@PathVariable Long id) {
        return ResponseEntity.ok(geneFunctionService.obtenerFuncionesPorGen(id));
    }

    /**
     * POST /genes/{id}/functions/{functionId} → Asociar una función a un gen.
     * Acceso: Solo ADMIN
     */
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/{id}/functions/{functionId}")
    public ResponseEntity<GeneFunctionOutDTO> asociarFuncion(
            @PathVariable Long id,
            @PathVariable Long functionId,
            @RequestBody GeneFunctionInDTO geneFunctionInDTO) {

        return ResponseEntity.ok(geneFunctionService.asociarFuncionAGen(id, functionId, geneFunctionInDTO));
    }

    /**
     * DELETE /genes/{id}/functions/{functionId} → Eliminar asociación gen-función.
     * Acceso: Solo ADMIN
     */
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}/functions/{functionId}")
    public ResponseEntity<GeneFunctionOutDTO> eliminarRelacion(
            @PathVariable Long id,
            @PathVariable Long functionId) {

        return geneFunctionService.eliminarRelacion(id, functionId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
