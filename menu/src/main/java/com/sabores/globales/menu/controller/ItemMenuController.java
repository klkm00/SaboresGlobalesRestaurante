package com.sabores.globales.menu.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.sabores.globales.menu.dto.ItemMenuRequest;
import com.sabores.globales.menu.dto.ItemMenuResponse;
import com.sabores.globales.menu.service.ItemMenuService;
import lombok.RequiredArgsConstructor;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/item-menu")
@RequiredArgsConstructor
@Tag(name = "Item Menu", description = "Gestión de items del menú")
public class ItemMenuController {

    private final ItemMenuService itemMenuService;

    @Operation(summary = "Listar todos los items del menú")
    @ApiResponse(responseCode = "200", description = "Lista obtenida correctamente")
    @GetMapping
    public ResponseEntity<List<ItemMenuResponse>> listarTodos() {
        return ResponseEntity.ok(itemMenuService.listarTodos());
    }

    @Operation(summary = "Listar items disponibles hoy")
    @ApiResponse(responseCode = "200", description = "Lista obtenida correctamente")
    @GetMapping("/disponibles")
    public ResponseEntity<List<ItemMenuResponse>> listarDisponibles() {
        return ResponseEntity.ok(itemMenuService.listarDisponibles());
    }

    @Operation(summary = "Listar items por origen culinario")
    @ApiResponse(responseCode = "200", description = "Lista obtenida correctamente")
    @GetMapping("/origen/{origenId}")
    public ResponseEntity<List<ItemMenuResponse>> listarPorOrigen(@PathVariable UUID origenId) {
        return ResponseEntity.ok(itemMenuService.listarPorOrigen(origenId));
    }

    @Operation(summary = "Buscar item por ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Item encontrado"),
        @ApiResponse(responseCode = "400", description = "Item no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ItemMenuResponse> buscarPorId(@PathVariable UUID id) {
        return ResponseEntity.ok(itemMenuService.buscarPorId(id));
    }

    @Operation(summary = "Agregar un item al menú")
    @ApiResponse(responseCode = "201", description = "Item agregado correctamente")
    @PostMapping
    public ResponseEntity<ItemMenuResponse> guardar(@RequestBody @Valid ItemMenuRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(itemMenuService.guardar(request));
    }

    @Operation(summary = "Actualizar un item del menú")
    @ApiResponse(responseCode = "200", description = "Item actualizado correctamente")
    @PutMapping("/{id}")
    public ResponseEntity<ItemMenuResponse> actualizar(@PathVariable UUID id,
                                                       @RequestBody @Valid ItemMenuRequest request) {
        return ResponseEntity.ok(itemMenuService.actualizar(id, request));
    }

    @Operation(summary = "Eliminar un item del menú")
    @ApiResponse(responseCode = "204", description = "Item eliminado correctamente")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable UUID id) {
        itemMenuService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}