package com.carrito.sbgl.Controller;

import java.util.List;
import java.util.Optional;

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

import com.carrito.sbgl.DTO.CarritoRequest;
import com.carrito.sbgl.DTO.CarritoResponse;
import com.carrito.sbgl.Service.CarritoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/carrito")
@RequiredArgsConstructor
@Tag(name = "Carritos", description = "Operaciones relacionadas con los pedidos y carritos")
public class CarritoController {
    private final CarritoService service;

    @GetMapping
    @Operation(summary = "Obtener carrito", description = "Devuelve una lista del carrito")
    public ResponseEntity<List<CarritoResponse>>  ObtenerTodos(){
        return ResponseEntity.ok(service.obtenerTodos());
    }
    @GetMapping("/{id}")
    @Operation(summary = "Obtener un carrito por id", description = "Devuelve un cierto carrito solicitado por su id")
    public ResponseEntity<CarritoResponse> obtenerporId(@PathVariable Long id){
        return service.obtenerPorId(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    @Operation(summary =  "Crear un Carrito", description = "Crear un nuevo carrito con los datos proporcionados")
    public ResponseEntity<CarritoResponse> crear(@RequestBody CarritoRequest dto){
        CarritoResponse ewResponse = service.guardar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(ewResponse);

    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar Carrito", description = "Actualiza los datos del carrito")
    public ResponseEntity<CarritoResponse> actualizar(@PathVariable Long id, @RequestBody CarritoRequest dto){
        Optional<CarritoResponse> carritoActualizado = service.actualizar(id, dto);
        return carritoActualizado.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Elimina un carrito", description = "Elimina los datos del carrito")
    public ResponseEntity<Void> eliminar(@PathVariable Long id){
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }


}
