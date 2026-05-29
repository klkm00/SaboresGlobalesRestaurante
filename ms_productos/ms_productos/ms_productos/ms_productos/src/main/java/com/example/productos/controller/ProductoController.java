
package com.example.productos.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import java.util.List;
import java.util.Optional;

import com.example.productos.service.ProductoService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import com.example.productos.dto.ProductoRequestDTO;
import com.example.productos.dto.ProductoResponseDTO;

@RestController
@RequestMapping("/api/v1/productos")
@RequiredArgsConstructor
public class ProductoController {

    private final ProductoService service;


    @GetMapping
    public ResponseEntity<List<ProductoResponseDTO>> obtenerTodos() {
        return ResponseEntity.ok(service.obtenerTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductoResponseDTO> obtenerPorId(@PathVariable Long id) {
        Optional<ProductoResponseDTO> producto = service.obtenerPorId(id);
        return producto.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ProductoResponseDTO> crear(@Valid @RequestBody ProductoRequestDTO p) {
        return ResponseEntity.status(201).body(service.crear(p));
    }


    @PutMapping("/{id}")
    public ResponseEntity<ProductoResponseDTO> actualizar(@PathVariable Long id, @Valid @RequestBody ProductoRequestDTO p) {
        Optional<ProductoResponseDTO> actualizado = service.actualizar(id, p);
        return actualizado.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}