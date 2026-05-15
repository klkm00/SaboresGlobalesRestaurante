package com.saboresGlobales.Delivery.delivery.Controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.saboresGlobales.Delivery.delivery.DTO.DeliveryRequestDTO;
import com.saboresGlobales.Delivery.delivery.DTO.DeliveryResponseDTO;
import com.saboresGlobales.Delivery.delivery.Service.DeliveryService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/delivery")
@RequiredArgsConstructor
public class DeliveryController {
    private final DeliveryService service;

    @GetMapping
    public ResponseEntity<List<DeliveryResponseDTO>> obtenerTodos(){
        return ResponseEntity.ok(service.obtenDeliveryRequestDTOs());
        
    }


    @GetMapping("/{id}")
    public ResponseEntity<DeliveryResponseDTO> obtenerPorID(@PathVariable Long id){
        return service.obtenerPorID(id)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<DeliveryResponseDTO> crear(
        @Valid @RequestBody DeliveryRequestDTO dto){
            return ResponseEntity.status(201).body(service.guardar(dto));
        }

    @DeleteMapping("/{id}/1")
    public ResponseEntity<Void> eliminar(@PathVariable Long id){
        if (service.obtenerPorID(id).isEmpty()){
            return ResponseEntity.notFound().build();
        }

        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<DeliveryResponseDTO>> buscarporRepartidor(
        @RequestParam String repartidor){
            return ResponseEntity.ok(service.buscarporRepartidor(repartidor));
    }
}
