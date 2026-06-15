package com.productos.productos.Controller;

import com.productos.productos.Repository.RecetaRepository;
import java.util.List;

import org.hibernate.annotations.DialectOverride.GeneratedColumn;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.productos.productos.DTO.RecetaRequestDTO;
import com.productos.productos.DTO.RecetaResponseDTO;
import com.productos.productos.Service.ProductoService;
import com.productos.productos.Service.RecetaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
@RestController
@RequestMapping("/api/v1/recetas")
@RequiredArgsConstructor
@Tag(name = "Recetas", description = "Operaciones CRUD para recetas")
public class RecetaController {

    private final RecetaService service;

    @GetMapping
    @Operation(summary = "Obtener todas las recetas", description = "Devuelve una lista de todas las recetas disponibles")
    public ResponseEntity<List<RecetaResponseDTO>> obtenerTodas() {
        return ResponseEntity.ok(service.obtenerTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RecetaResponseDTO> obtenerPorId(@PathVariable Long id) {
        return service.obtenerPorId(id)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Crear receta", description = "Crea una nueva receta")
    public ResponseEntity<RecetaResponseDTO> guardar(@Valid @RequestBody RecetaRequestDTO dto) {
        return ResponseEntity.status(201).body(service.guardar(dto));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar receta", description = "Actualiza los detalles de una receta existente por su ID")
    public ResponseEntity<RecetaResponseDTO>actualizar(@PathVariable Long id, @Valid @RequestBody RecetaRequestDTO dto) {
        return service.actualizar(id, dto).map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar receta", description = "Elimina una receta existente por su ID")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (service.obtenerPorId(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/nombre/{nombreInsumo}")
    @Operation(summary = "Buscar recetas por nombre de insumo", description = "Devuelve una lista de recetas que contienen un insumo específico")
    public ResponseEntity<List<RecetaResponseDTO>> buscarPorNombreInsumo(@PathVariable String nombreInsumo) {
        return ResponseEntity.ok(service.obtenerRecetasPorNombreInsumo(nombreInsumo));
    }

    @GetMapping("/producto/{id}")
    @Operation(summary = "Buscar receta por el id del producto", description = "Busca Receta por el ID de su producto")
    public ResponseEntity<List<RecetaResponseDTO>> buscarPorProductoID(@PathVariable Long id){
        return ResponseEntity.ok(service.obtenerRecetasPorProductoId(id));
    }
}
