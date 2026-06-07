package com.saboresGlobales.Delivery.delivery.Controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.saboresGlobales.Delivery.delivery.DTO.DeliveryRequestDTO;
import com.saboresGlobales.Delivery.delivery.DTO.DeliveryResponseDTO;
import com.saboresGlobales.Delivery.delivery.Service.DeliveryService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/delivery")
@RequiredArgsConstructor
@Tag(name = "Delivery Controller", description = "Controlador para gestionar pedidos, productos y usuarios en Sabores Globales")
public class DeliveryController {
    private final DeliveryService service;

    @GetMapping
    @Operation(summary = "Obtener todos los pedidos", description = "Devuelve una lista de todos los pedidos registrados en el sistema")
    public ResponseEntity<List<DeliveryResponseDTO>> obtenerTodos(){
        return ResponseEntity.ok(service.obtenDeliveryRequestDTOs());
        
    }


    @GetMapping("/{id}")
    @Operation(summary = "Obtener pedido por ID", description = "Devuelve un pedido específico según su ID")
    public ResponseEntity<DeliveryResponseDTO> obtenerPorID(@PathVariable Long id){
        return service.obtenerPorID(id)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Crear nuevo pedido", description = "Crea un nuevo pedido en el sistema")
    public ResponseEntity<DeliveryResponseDTO> crear(
        @Valid @RequestBody DeliveryRequestDTO dto){
            return ResponseEntity.status(201).body(service.guardar(dto));
        }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar pedido", description = "Elimina un pedido específico según su ID")
    public ResponseEntity<Void> eliminar(@PathVariable Long id){
        if (service.obtenerPorID(id).isEmpty()){
            return ResponseEntity.notFound().build();
        }

        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar pedido", description = "Actualiza un pedido específico según su ID")
    public ResponseEntity<DeliveryResponseDTO> actualizar(
        @PathVariable Long id,
        @Valid @RequestBody DeliveryRequestDTO dto){
            return service.actualizar(id, dto)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/buscar/{repartidor}")
    @Operation(summary = "Buscar pedidos por repartidor", description = "Devuelve una lista de pedidos asociados a un repartidor específico")
    public ResponseEntity<List<DeliveryResponseDTO>> buscarporRepartidor(
        @PathVariable String repartidor){
            return ResponseEntity.ok(service.buscarporRepartidor(repartidor));
    }
}
