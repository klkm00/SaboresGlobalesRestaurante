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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/origen")
@RequiredArgsConstructor
@Tag(name = "Origen", description = "Gestión de orígenes culinarios del menú")
public class OrigenMenuController {

    private final OrigenMenuService origenMenuService;

    @Operation(summary = "Listar orígenes activos", description = "Retorna todos los orígenes culinarios activos")
    @ApiResponse(responseCode = "200", description = "Lista obtenida correctamente")
    @GetMapping
    public ResponseEntity<List<OrigenResponse>> listarActivos() {
        return ResponseEntity.ok(origenMenuService.listarActivos());
    }

    @Operation(summary = "Buscar origen por ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Origen encontrado"),
        @ApiResponse(responseCode = "400", description = "Origen no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<OrigenResponse> buscarPorId(@PathVariable UUID id) {
        return ResponseEntity.ok(origenMenuService.buscarPorId(id));
    }

    @Operation(summary = "Crear un nuevo origen culinario")
    @ApiResponse(responseCode = "201", description = "Origen creado correctamente")
    @PostMapping
    public ResponseEntity<OrigenResponse> guardar(@RequestBody @Valid OrigenRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(origenMenuService.guardar(request));
    }

    @Operation(summary = "Actualizar un origen existente")
    @ApiResponse(responseCode = "200", description = "Origen actualizado correctamente")
    @PutMapping("/{id}")
    public ResponseEntity<OrigenResponse> actualizar(@PathVariable UUID id,
                                                     @RequestBody @Valid OrigenRequest request) {
        return ResponseEntity.ok(origenMenuService.actualizar(id, request));
    }

    @Operation(summary = "Eliminar un origen por ID")
    @ApiResponse(responseCode = "204", description = "Origen eliminado correctamente")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable UUID id) {
        origenMenuService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}