package com.saboresGlobales.usuarios.Sabores_Globales_Usuarios.Controller;

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

import com.saboresGlobales.usuarios.Sabores_Globales_Usuarios.DTO.UsuarioReponseDTO;
import com.saboresGlobales.usuarios.Sabores_Globales_Usuarios.DTO.UsuarioRequestDTO;
import com.saboresGlobales.usuarios.Sabores_Globales_Usuarios.Service.UsuarioService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/usuarios")
@RequiredArgsConstructor
public class UsuarioController {
    
    private final UsuarioService usuarioService;
    @GetMapping
 public ResponseEntity<List<UsuarioReponseDTO>> obtenerTodos(){
    return ResponseEntity.ok(usuarioService.obtenerTodos());
 }
 @GetMapping("/{id}")
 public ResponseEntity<UsuarioReponseDTO> obtenerPorId(@PathVariable Long id){
    return usuarioService.obtenerporId(id)
    .map(ResponseEntity::ok)
    .orElse(ResponseEntity.notFound().build());
 }


 @PostMapping
 public ResponseEntity<UsuarioReponseDTO> create(
    @Valid @RequestBody UsuarioRequestDTO dto
 ){
    return ResponseEntity.status(201).body(usuarioService.guardar(dto));
 }

 @PutMapping("/{id}")
 public ResponseEntity<UsuarioReponseDTO> actualizar(@PathVariable Long id,
    @Valid @RequestBody UsuarioRequestDTO dto) {
        return usuarioService.actualizar(id,dto)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
    }
 
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (usuarioService.obtenerporId(id).isEmpty()){
            return ResponseEntity.notFound().build();
        }
        usuarioService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/rut/{rut}")
    public ResponseEntity<List<UsuarioReponseDTO>> buscarPorRut(
        @PathVariable String rut){
            return ResponseEntity.ok(usuarioService.buscarPorRut(rut));
        }
    
        
    @GetMapping("/nombres/{nombres}")
    public ResponseEntity<List<UsuarioReponseDTO>> buscarPorNombre(
        @PathVariable String nombres){
            return ResponseEntity.ok(usuarioService.buscarPorNombre(nombres));
        }

    @GetMapping("/rol/{id}")
    public ResponseEntity<List<UsuarioReponseDTO>> buscarPorRol(
        @PathVariable Long id){
            return ResponseEntity.ok(usuarioService.buscarPorRol(id));
        }
    
}
