package com.genomebank.controllers;

import com.genomebank.entities.Function;
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
    @PostMapping("/crear")
    public ResponseEntity<Function> crearFunction(@RequestBody Function function) {
        return ResponseEntity.ok(functionService.crearFuncion(function));
    }

    /**
     * Actualizar una función completamente.
     */
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<Function> actualizarFunction(@PathVariable Long id, @RequestBody Function function) {
        return functionService.actualizarFuncion(id, function)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Actualizar parcialmente una función.
     */
    /*@PatchMapping("/actualizar_parcial/{id}")
    public ResponseEntity<Function> actualizarFunctionParcial(@PathVariable Long id, @RequestBody Function function) {
        return functionService.actualiza(id, function)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }*/

    /**
     * Consultar todas las funciones.
     */
    @GetMapping("/consultar_todos")
    public ResponseEntity<List<Function>> obtenerFunciones() {
        return ResponseEntity.ok(functionService.obtenerFunciones());
    }

    /**
     * Consultar una función por ID.
     */
    @GetMapping("/consultar/{id}")
    public ResponseEntity<Function> obtenerPorId(@PathVariable Long id) {
        return functionService.obtenerFuncionPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Eliminar una función.
     */
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminarFunction(@PathVariable Long id) {
        functionService.eliminarFuncion(id);
        return ResponseEntity.noContent().build();
    }
}
