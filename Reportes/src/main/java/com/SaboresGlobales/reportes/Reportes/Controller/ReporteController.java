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

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/reportes")
@RequiredArgsConstructor
public class ReporteController {
        private final ReporteService service;
      @GetMapping
      public ResponseEntity<List<ReportesResponseDTO>> obtenerTodos(){
        return ResponseEntity.ok(service.obtenerDtos());
      }
      @GetMapping("/{id}")
      public ResponseEntity<ReportesResponseDTO> obtenerporID(@PathVariable Long id){
        return service.obtenerporID(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());

      }
      @PostMapping
      public ResponseEntity<ReportesResponseDTO> crear(
        @Valid @RequestBody ReportesRequestDTO dto) {
            return ResponseEntity.status(201).body(service.guardar(dto));
        }
      
      
      @DeleteMapping("/{id}")
      public ResponseEntity<Void> eliminar(@PathVariable Long id){
        if (service.obtenerporID(id).isEmpty()){
            return ResponseEntity.notFound().build();
        }
        service.eliminar(id);
        return ResponseEntity.noContent().build();
      }

      @GetMapping("/buscar/{pedido}")
      public ResponseEntity<List<ReportesResponseDTO>> buscarporPedido(
        @PathVariable String pedido){
            return ResponseEntity.ok(service.buscarporPedido(pedido));
        }
      
    }
