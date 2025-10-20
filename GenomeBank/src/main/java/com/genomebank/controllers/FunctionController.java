package com.genomebank.controllers;

import com.genomebank.dtos.FunctionInDTO;
import com.genomebank.dtos.FunctionOutDTO;
import com.genomebank.services.IFunctionService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/functions")
public class FunctionController {

    private final IFunctionService functionService;

    public FunctionController(IFunctionService functionService) {
        this.functionService = functionService;
    }
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping
    public ResponseEntity<List<FunctionOutDTO>> listarFunciones(
            @RequestParam(value = "code", required = false) String code,
            @RequestParam(value = "category", required = false) String category
    ) {
        return ResponseEntity.ok(functionService.obtenerFunciones(code, category));
    }

    @GetMapping("/{id}")
    public ResponseEntity<FunctionOutDTO> obtenerFuncion(@PathVariable Long id) {
        return functionService.obtenerFuncionPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<FunctionOutDTO> crearFuncion(@RequestBody FunctionInDTO dto) {
        return ResponseEntity.ok(functionService.crearFuncion(dto));
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<FunctionOutDTO> actualizarFuncion(
            @PathVariable Long id,
            @RequestBody FunctionInDTO dto
    ) {
        return functionService.actualizarFuncion(id, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarFuncion(@PathVariable Long id) {
        functionService.eliminarFuncion(id);
        return ResponseEntity.noContent().build();
    }
}
