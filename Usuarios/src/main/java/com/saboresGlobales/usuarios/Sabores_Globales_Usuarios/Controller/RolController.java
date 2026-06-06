package com.saboresGlobales.usuarios.Sabores_Globales_Usuarios.Controller;



import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.saboresGlobales.usuarios.Sabores_Globales_Usuarios.Service.RolService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import com.saboresGlobales.usuarios.Sabores_Globales_Usuarios.DTO.RolRequesrDTO;
import com.saboresGlobales.usuarios.Sabores_Globales_Usuarios.DTO.RolResponseDTO;


@RestController
@RequestMapping("/api/v1/roles")
@RequiredArgsConstructor
@Tag(name = "Roles", description = "Operaciones relacionadas con los roles de usuario")
public class RolController {

    private final RolService rolService;

    @GetMapping
    @Operation(summary = "Listar roles", description = "Obtiene una lista de todos los roles disponibles")
    public ResponseEntity<List<RolResponseDTO>> listar() {
        return ResponseEntity.ok(rolService.obtenerTodas());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener rol por ID", description = "Obtiene los detalles de un rol específico por su ID")
    public ResponseEntity<RolResponseDTO> obtenerPorId(@PathVariable Long id) {

        Optional<RolResponseDTO> rol = rolService.obtenerPorId(id);

        return rol.map(ResponseEntity::ok)
                  .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Crear rol", description = "Crea un nuevo rol con los datos proporcionados")
    public ResponseEntity<RolResponseDTO> crear(
            @RequestBody RolRequesrDTO dto) {

        RolResponseDTO nuevoRol = rolService.guardar(dto);

        return ResponseEntity.status(HttpStatus.CREATED)
                             .body(nuevoRol);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar rol", description = "Actualiza los datos de un rol específico por su ID")
    public ResponseEntity<RolResponseDTO> actualizar(
            @PathVariable Long id,
            @RequestBody RolRequesrDTO dto) {

        Optional<RolResponseDTO> rolActualizado =
                rolService.actualizar(id, dto);

        return rolActualizado.map(ResponseEntity::ok)
                             .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar rol", description = "Elimina un rol específico por su ID")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {

        rolService.eliminar(id);

        return ResponseEntity.noContent().build();
    }
}