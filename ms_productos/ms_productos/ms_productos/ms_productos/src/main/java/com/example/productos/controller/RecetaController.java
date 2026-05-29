package com.example.productos.controller;

import com.example.productos.service.ProductoService;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.productos.dto.RecetaRequestDTO;
import com.example.productos.dto.RecetaResponseDTO;
import com.example.productos.service.RecetaService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/recetas")
@RequiredArgsConstructor
public class RecetaController {
    private final ProductoService productoService;
    private final RecetaService service;

    @GetMapping
    public ResponseEntity<List<RecetaResponseDTO>> obtenerTodas() {
        return ResponseEntity.ok(service.obtenerTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseEntity<RecetaResponseDTO>> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.obtenerPorId(id)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build()));
    }

    @PostMapping
    public ResponseEntity<RecetaResponseDTO> guardar(@Valid @RequestBody RecetaRequestDTO dto) {
        return ResponseEntity.status(201).body(service.guardar(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseEntity<RecetaResponseDTO>> actualizar(@PathVariable Long id, @Valid @RequestBody RecetaRequestDTO dto) {
        return ResponseEntity.ok(service.actualizar(id, dto)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (productoService.obtenerPorId(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/nombre/{nombreInsumo}")
    public ResponseEntity<List<RecetaResponseDTO>> buscarPorNombreInsumo(@PathVariable String nombreInsumo) {
        return ResponseEntity.ok(service.obtenerRecetasPorNombreInsumo(nombreInsumo));
    }
}
