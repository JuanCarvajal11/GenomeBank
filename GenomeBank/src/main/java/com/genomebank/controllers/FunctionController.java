package com.genomebank.controllers;

import com.genomebank.dtos.FunctionInDTO;
import com.genomebank.dtos.FunctionOutDTO;
import com.genomebank.services.IFunctionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/function")
public class FunctionController {

    private final IFunctionService functionService;

    public FunctionController(IFunctionService functionService) {
        this.functionService = functionService;
    }

    /**
     * Crear una nueva función.
     */
    @PostMapping("/create")
    public ResponseEntity<FunctionOutDTO> crearFunction(@RequestBody FunctionInDTO functionInDTO) {
        return ResponseEntity.ok(functionService.crearFuncion(functionInDTO));
    }

    /**
     * Actualizar una función completamente.
     */
    @PutMapping("/update/{id}")
    public ResponseEntity<FunctionOutDTO> actualizarFunction(@PathVariable Long id,
                                                             @RequestBody FunctionInDTO functionInDTO) {
        return functionService.actualizarFuncion(id, functionInDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Consultar todas las funciones.
     */
    @GetMapping("/")
    public ResponseEntity<List<FunctionOutDTO>> obtenerFunciones() {
        return ResponseEntity.ok(functionService.obtenerFunciones());
    }

    /**
     * Consultar una función por ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<FunctionOutDTO> obtenerPorId(@PathVariable Long id) {
        return functionService.obtenerFuncionPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Eliminar una función.
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> eliminarFunction(@PathVariable Long id) {
        functionService.eliminarFuncion(id);
        return ResponseEntity.noContent().build();
    }
}
