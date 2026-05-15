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

@RestController
@RequestMapping("/api/item-menu")
@RequiredArgsConstructor
public class ItemMenuController {

    private final ItemMenuService itemMenuService;

    @GetMapping
    public ResponseEntity<List<ItemMenuResponse>> listarTodos() {
        return ResponseEntity.ok(itemMenuService.listarTodos());
    }

    @GetMapping("/disponibles")
    public ResponseEntity<List<ItemMenuResponse>> listarDisponibles() {
        return ResponseEntity.ok(itemMenuService.listarDisponibles());
    }

    @GetMapping("/origen/{origenId}")
    public ResponseEntity<List<ItemMenuResponse>> listarPorOrigen(@PathVariable UUID origenId) {
        return ResponseEntity.ok(itemMenuService.listarPorOrigen(origenId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItemMenuResponse> buscarPorId(@PathVariable UUID id) {
        return ResponseEntity.ok(itemMenuService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<ItemMenuResponse> guardar(@RequestBody @Valid ItemMenuRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(itemMenuService.guardar(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ItemMenuResponse> actualizar(@PathVariable UUID id,
                                                       @RequestBody @Valid ItemMenuRequest request) {
        return ResponseEntity.ok(itemMenuService.actualizar(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable UUID id) {
        itemMenuService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
    

