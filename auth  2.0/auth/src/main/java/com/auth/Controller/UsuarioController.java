package com.auth.Controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.auth.DTO.UsuarioReponseDTO;
import com.auth.DTO.UsuarioRequestDTO;
import com.auth.Service.UsuarioService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/usuarios")
@RequiredArgsConstructor
@Tag(name = "Usuarios", description = "Operaciones relacionadas con los usuarios")
public class UsuarioController {
    
    private final UsuarioService usuarioService;
    @GetMapping
    @Operation(summary = "Listar usuarios", description = "Obtiene una lista de todos los usuarios registrados")
    public ResponseEntity<List<UsuarioReponseDTO>> obtenerTodos(){
    return ResponseEntity.ok(usuarioService.obtenerTodos());
 }
 @GetMapping("/{id}")
 @Operation(summary = "Obtener usuario por ID", description = "Obtiene los detalles de un usuario específico por su ID")
 public ResponseEntity<UsuarioReponseDTO> obtenerPorId(@PathVariable Long id){
    return usuarioService.obtenerporId(id)
    .map(ResponseEntity::ok)
    .orElse(ResponseEntity.notFound().build());
 }


 @PostMapping
    @Operation(summary = "Crear usuario", description = "Crea un nuevo usuario con los datos proporcionados")
 public ResponseEntity<UsuarioReponseDTO> create(
    @Valid @RequestBody UsuarioRequestDTO dto
 ){
    return ResponseEntity.status(201).body(usuarioService.guardar(dto));
 }

 @PutMapping("/{id}")
 @Operation(summary = "Actualizar usuario", description = "Actualiza los datos de un usuario específico por su ID")
 public ResponseEntity<UsuarioReponseDTO> actualizar(@PathVariable Long id,
    @Valid @RequestBody UsuarioRequestDTO dto) {
        return usuarioService.actualizar(id,dto)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
    }
 
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar usuario", description = "Elimina un usuario específico por su ID")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (usuarioService.obtenerporId(id).isEmpty()){
            return ResponseEntity.notFound().build();
        }
        usuarioService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/rut/{rut}")
    @Operation(summary = "Buscar usuario por RUT", description = "Busca usuarios por su RUT")
    public ResponseEntity<List<UsuarioReponseDTO>> buscarPorRut(
        @PathVariable String rut){
            return ResponseEntity.ok(usuarioService.buscarPorRut(rut));
        }
    
        
    @GetMapping("/nombres/{nombres}")
        @Operation(summary = "Buscar usuario por nombre", description = "Busca usuarios por su nombre completo o parcial")
    public ResponseEntity<List<UsuarioReponseDTO>> buscarPorNombre(
        @PathVariable String nombres){
            return ResponseEntity.ok(usuarioService.buscarPorNombre(nombres));
        }

    @GetMapping("/rol/{id}")
    @Operation(summary = "Buscar usuario por rol", description = "Busca usuarios por el ID de su rol")
    public ResponseEntity<List<UsuarioReponseDTO>> buscarPorRol(
        @PathVariable Long id){
            return ResponseEntity.ok(usuarioService.buscarPorRol(id));
        }

  

    
}
