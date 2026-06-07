package com.sabores.globales.menu.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.sabores.globales.menu.dto.ItemMenuRequest;
import com.sabores.globales.menu.dto.ItemMenuResponse;
import com.sabores.globales.menu.service.ItemMenuService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/item-menu")
@RequiredArgsConstructor
@Tag(name = "ItemMenu", description = "Operaciones relacionadas con los ítems del menú")
public class ItemMenuController {

    private final ItemMenuService itemMenuService;

    @GetMapping
    @Operation(summary = "Listar todos los ítems del menú", description = "Obtiene una lista de todos los ítems del menú, incluyendo los que no están disponibles actualmente.")
    public ResponseEntity<List<ItemMenuResponse>> listarTodos() {
        return ResponseEntity.ok(itemMenuService.listarTodos());
    }

    @GetMapping("/disponibles")
    @Operation(summary = "Listar ítems disponibles", description = "Obtiene una lista de los ítems del menú que están disponibles actualmente.")
    public ResponseEntity<List<ItemMenuResponse>> listarDisponibles() {
        return ResponseEntity.ok(itemMenuService.listarDisponibles());
    }

    @GetMapping("/origen/{origenId}")
    @Operation(summary = "Listar ítems por origen", description = "Obtiene una lista de ítems del menú filtrados por su origen.")
    public ResponseEntity<List<ItemMenuResponse>> listarPorOrigen(@PathVariable UUID origenId) {
        return ResponseEntity.ok(itemMenuService.listarPorOrigen(origenId));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar ítem por ID", description = "Obtiene un ítem del menú por su ID.")
    public ResponseEntity<ItemMenuResponse> buscarPorId(@PathVariable UUID id) {
        return ResponseEntity.ok(itemMenuService.buscarPorId(id));
    }

    @PostMapping
    @Operation(summary = "Crear ítem", description = "Crea un nuevo ítem en el menú.")
    public ResponseEntity<ItemMenuResponse> guardar(@RequestBody @Valid ItemMenuRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(itemMenuService.guardar(request));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar ítem", description = "Actualiza un ítem existente en el menú.")
    public ResponseEntity<ItemMenuResponse> actualizar(@PathVariable UUID id,
                                                       @RequestBody @Valid ItemMenuRequest request) {
        return ResponseEntity.ok(itemMenuService.actualizar(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar ítem", description = "Elimina un ítem del menú.")
    public ResponseEntity<Void> eliminar(@PathVariable UUID id) {
        itemMenuService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
    

