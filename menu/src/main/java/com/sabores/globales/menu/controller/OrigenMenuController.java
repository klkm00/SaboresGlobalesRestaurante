package com.sabores.globales.menu.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.sabores.globales.menu.dto.OrigenRequest;
import com.sabores.globales.menu.dto.OrigenResponse;
import com.sabores.globales.menu.service.OrigenMenuService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/origen")
@RequiredArgsConstructor
@Tag(name = "OrigenMenu", description = "Operaciones relacionadas con los orígenes del menú")
public class OrigenMenuController {

    private final OrigenMenuService origenMenuService;

    @GetMapping
    @Operation(summary = "Listar todos los orígenes", description = "Obtiene una lista de todos los orígenes del menú, incluyendo los que no están activos actualmente.")
    public ResponseEntity<List<OrigenResponse>> listarActivos() {
        return ResponseEntity.ok(origenMenuService.listarActivos());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar origen por ID", description = "Obtiene un origen del menú por su ID.")
    public ResponseEntity<OrigenResponse> buscarPorId(@PathVariable UUID id) {
        return ResponseEntity.ok(origenMenuService.buscarPorId(id));
    }

    @PostMapping
    @Operation(summary = "Crear origen", description = "Crea un nuevo origen para el menú.")
    public ResponseEntity<OrigenResponse> guardar(@RequestBody @Valid OrigenRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(origenMenuService.guardar(request));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar origen", description = "Actualiza un origen existente del menú.")
    public ResponseEntity<OrigenResponse> actualizar(@PathVariable UUID id,
                                                     @RequestBody @Valid OrigenRequest request) {
        return ResponseEntity.ok(origenMenuService.actualizar(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar origen", description = "Elimina un origen del menú.")
    public ResponseEntity<Void> eliminar(@PathVariable UUID id) {
        origenMenuService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}