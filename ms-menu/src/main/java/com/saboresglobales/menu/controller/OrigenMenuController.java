package com.saboresglobales.menu.controller;

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
import com.sabores.globales.menu.model.OrigenMenuModel;
import com.sabores.globales.menu.service.OrigenMenuService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/origen")
@RequiredArgsConstructor
public class OrigenMenuController {

    private final OrigenMenuService origenMenuService;

    @GetMapping
    public ResponseEntity<List<OrigenMenuModel>> listarActivos() {
        return ResponseEntity.ok(origenMenuService.listarActivos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrigenMenuModel> buscarPorId(@PathVariable UUID id) {
        return ResponseEntity.ok(origenMenuService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<OrigenMenuModel> guardar(@RequestBody @Valid OrigenMenuModel origen) {
        return ResponseEntity.status(HttpStatus.CREATED).body(origenMenuService.guardar(origen));
    }

     @PutMapping("/{id}")
    public ResponseEntity<OrigenMenuModel> actualizar(@PathVariable UUID id, @RequestBody @Valid OrigenMenuModel origen) {
        return ResponseEntity.ok(origenMenuService.actualizar(id, origen));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable UUID id) {
        origenMenuService.eliminar(id);
        return ResponseEntity.noContent().build();
    } 
}
