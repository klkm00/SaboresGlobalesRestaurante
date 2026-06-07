package com.SaboresGlobales.reportes.Reportes.Controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.SaboresGlobales.reportes.Reportes.DTO.ReportesRequestDTO;
import com.SaboresGlobales.reportes.Reportes.DTO.ReportesResponseDTO;
import com.SaboresGlobales.reportes.Reportes.Service.ReporteService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/reportes")
@RequiredArgsConstructor
@Tag (name = "Reportes", description = "Operaciones relacionadas con los reportes de pedidos")
public class ReporteController {
        private final ReporteService service;
      @GetMapping
      @Operation(summary = "Obtener todos los reportes", description = "Devuelve una lista de todos los reportes de pedidos registrados en el sistema")
      public ResponseEntity<List<ReportesResponseDTO>> obtenerTodos(){
        return ResponseEntity.ok(service.obtenerDtos());
      }
      @GetMapping("/{id}")
      @Operation(summary = "Obtener reporte por ID", description = "Devuelve un reporte específico por su ID")
      public ResponseEntity<ReportesResponseDTO> obtenerporID(@PathVariable Long id){
        return service.obtenerporID(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());

      }
      @PostMapping
      @Operation(summary = "Crear reporte", description = "Crea un nuevo reporte de pedido")
      public ResponseEntity<ReportesResponseDTO> crear(
        @Valid @RequestBody ReportesRequestDTO dto) {
            return ResponseEntity.status(201).body(service.guardar(dto));
        }
      
      
      @DeleteMapping("/{id}")
      @Operation(summary = "Eliminar reporte", description = "Elimina un reporte específico por su ID")
      public ResponseEntity<Void> eliminar(@PathVariable Long id){
        if (service.obtenerporID(id).isEmpty()){
            return ResponseEntity.notFound().build();
        }
        service.eliminar(id);
        return ResponseEntity.noContent().build();
      }

      @GetMapping("/buscar/{pedido}")
      @Operation(summary = "Buscar reportes por pedido", description = "Devuelve una lista de reportes asociados a un pedido específico")
      public ResponseEntity<List<ReportesResponseDTO>> buscarporPedido(
        @PathVariable String pedido){
            return ResponseEntity.ok(service.buscarporPedido(pedido));
        }
      
    }
