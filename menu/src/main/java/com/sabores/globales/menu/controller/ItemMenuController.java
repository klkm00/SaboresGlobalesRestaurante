package com.sabores.globales.menu.controller;

import java.util.List;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.sabores.globales.menu.model.ItemMenuModel;
import com.sabores.globales.menu.service.ItemMenuService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/api/item-menu")
@RequiredArgsConstructor

public class ItemMenuController {

    private final ItemMenuService itemMenuService;

    @GetMapping
    public ResponseEntity<List<ItemMenuModel>> listarTodos() {
        return ResponseEntity.ok(itemMenuService.listarTodos());
    }

    @GetMapping("/disponibles")
    public ResponseEntity<List<ItemMenuModel>> listarDisponibles() {
        return ResponseEntity.ok(itemMenuService.listarDisponibles());
    }

    @GetMapping("/origen/{origenId}")
    public ResponseEntity<List<ItemMenuModel>> listarPorOrigen(@PathVariable UUID origenId) {
        return ResponseEntity.ok(itemMenuService.listarPorOrigen(origenId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItemMenuModel> buscarPorId(@PathVariable UUID id) {
        return ResponseEntity.ok(itemMenuService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<ItemMenuModel> guardar(@RequestBody @Valid ItemMenuModel item) {
        return ResponseEntity.status(HttpStatus.CREATED).body(itemMenuService.guardar(item));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ItemMenuModel> actualizar(@PathVariable UUID id, @RequestBody @Valid ItemMenuModel item) {
        return ResponseEntity.ok(itemMenuService.actualizar(id, item));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable UUID id) {
        itemMenuService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
    

