package com.sabores.globales.menu.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.sabores.globales.menu.dto.OrigenRequest;
import com.sabores.globales.menu.dto.OrigenResponse;
import com.sabores.globales.menu.service.OrigenMenuService;
import lombok.RequiredArgsConstructor;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/origen")
@RequiredArgsConstructor
public class OrigenMenuController {

    private final OrigenMenuService origenMenuService;

    @GetMapping
    public ResponseEntity<List<OrigenResponse>> listarActivos() {
        return ResponseEntity.ok(origenMenuService.listarActivos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrigenResponse> buscarPorId(@PathVariable UUID id) {
        return ResponseEntity.ok(origenMenuService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<OrigenResponse> guardar(@RequestBody @Valid OrigenRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(origenMenuService.guardar(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrigenResponse> actualizar(@PathVariable UUID id,
                                                     @RequestBody @Valid OrigenRequest request) {
        return ResponseEntity.ok(origenMenuService.actualizar(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable UUID id) {
        origenMenuService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}