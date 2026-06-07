
package com.example.ms_inventario.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.*;
import java.util.*;
import com.example.ms_inventario.service.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import com.example.ms_inventario.dto.InventarioRequestDTO;
import com.example.ms_inventario.dto.InventarioResponseDTO;

@RestController
@RequestMapping("/inventario")
@RequiredArgsConstructor
@Tag(name = "Inventario", description = "Operaciones relacionadas con el inventario de insumos")
public class InventarioController {

    private final InventarioService service;
    
    @GetMapping
    @Operation(summary = "Obtener todos los registros de inventario", description = "Devuelve una lista de todos los registros de inventario disponibles en la base de datos.")
    public ResponseEntity<List<InventarioResponseDTO>> getAll(){
        return ResponseEntity.ok(service.ObtenerTodos());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener un registro de inventario por ID", description = "Devuelve un registro de inventario específico según su ID.")
    public ResponseEntity<InventarioResponseDTO> getById(@PathVariable Long id){
        return service.ObtenerPorId(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build()); 
    }

    @PostMapping
    @Operation(summary = "Crear un nuevo registro de inventario", description = "Crea un nuevo registro de inventario con los datos proporcionados.")
    public ResponseEntity<InventarioResponseDTO> create(@Valid @RequestBody InventarioRequestDTO dto){
        return ResponseEntity.status(201).body(service.Guardar(dto));
       
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un registro de inventario", description = "Actualiza un registro de inventario específico según su ID.")
    public ResponseEntity<InventarioResponseDTO> update(@PathVariable Long id, @Valid
        @RequestBody InventarioRequestDTO dto){
        return service.Actualizar(id, dto)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
        
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un registro de inventario", description = "Elimina un registro de inventario específico según su ID.")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/insumo/{insumo}")
    @Operation(summary = "Obtener registros de inventario por insumo", description = "Devuelve una lista de registros de inventario que coinciden con el nombre del insumo proporcionado.")
    public ResponseEntity<List<InventarioResponseDTO>> getByInsumo(@PathVariable String insumo){
        return ResponseEntity.ok(service.ObtenerPorInsumo(insumo));
    }




}
