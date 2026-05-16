package com.saboresGlobales.usuarios.Sabores_Globales_Usuarios.Controller;



import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.saboresGlobales.usuarios.Sabores_Globales_Usuarios.Service.RolService;

import lombok.RequiredArgsConstructor;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import com.saboresGlobales.usuarios.Sabores_Globales_Usuarios.DTO.RolRequesrDTO;
import com.saboresGlobales.usuarios.Sabores_Globales_Usuarios.DTO.RolResponseDTO;


@RestController
@RequestMapping("/api/v1/roles")
@RequiredArgsConstructor
public class RolController {

    private final RolService rolService;

    @GetMapping
    public ResponseEntity<List<RolResponseDTO>> listar() {
        return ResponseEntity.ok(rolService.obtenerTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RolResponseDTO> obtenerPorId(@PathVariable Long id) {

        Optional<RolResponseDTO> rol = rolService.obtenerPorId(id);

        return rol.map(ResponseEntity::ok)
                  .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<RolResponseDTO> crear(
            @RequestBody RolRequesrDTO dto) {

        RolResponseDTO nuevoRol = rolService.guardar(dto);

        return ResponseEntity.status(HttpStatus.CREATED)
                             .body(nuevoRol);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RolResponseDTO> actualizar(
            @PathVariable Long id,
            @RequestBody RolRequesrDTO dto) {

        Optional<RolResponseDTO> rolActualizado =
                rolService.actualizar(id, dto);

        return rolActualizado.map(ResponseEntity::ok)
                             .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {

        rolService.eliminar(id);

        return ResponseEntity.noContent().build();
    }
}