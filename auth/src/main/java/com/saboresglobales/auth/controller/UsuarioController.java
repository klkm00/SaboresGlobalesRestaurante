package com.saboresglobales.auth.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.saboresglobales.auth.dto.LoginRequest;
import com.saboresglobales.auth.dto.LoginResponse;
import com.saboresglobales.auth.dto.RecuperarContrasenaRequest;
import com.saboresglobales.auth.dto.UsuarioRequest;
import com.saboresglobales.auth.dto.UsuarioResponse;
import com.saboresglobales.auth.service.UsuarioService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/usuario")
@RequiredArgsConstructor
@Tag(name = "UsuarioController", description = "Controlador para la gestión de usuarios y autenticación")
public class UsuarioController {

    private final UsuarioService usuarioService;

    // POST /api/usuario/registro
    @PostMapping("/registro")
    @Operation(summary = "Registrar un nuevo usuario", description = "Permite registrar un nuevo usuario en el sistema")
    public ResponseEntity<UsuarioResponse> registrar(@RequestBody @Valid UsuarioRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.registrar(request));
    }

    // POST /api/usuario/login
    @PostMapping("/login")
    @Operation(summary = "Iniciar sesión", description = "Permite iniciar sesión en el sistema")
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginRequest request) {
        return ResponseEntity.ok(usuarioService.login(request));
    }

    // GET /api/usuario
    @GetMapping
    @Operation(summary = "Listar todos los usuarios", description = "Permite listar todos los usuarios del sistema")
    public ResponseEntity<List<UsuarioResponse>> listarTodos() {
        return ResponseEntity.ok(usuarioService.listarTodos());
    }

    // GET /api/usuario/{idUsuario}
    @GetMapping("/{idUsuario}")
    @Operation(summary = "Buscar usuario por ID", description = "Permite buscar un usuario específico por su ID")
    public ResponseEntity<UsuarioResponse> buscarPorId(@PathVariable UUID idUsuario) {
        return ResponseEntity.ok(usuarioService.buscarPorId(idUsuario));
    }

    // GET /api/usuario/correo/{correo}
    @GetMapping("/correo/{correo}")
    @Operation(summary = "Buscar usuario por correo", description = "Permite buscar un usuario específico por su correo electrónico")
    public ResponseEntity<UsuarioResponse> buscarPorCorreo(@PathVariable String correo) {
        return ResponseEntity.ok(usuarioService.buscarPorCorreo(correo));
    }

    // PUT /api/usuario/{idUsuario}
    @PutMapping("/{idUsuario}")
    @Operation(summary = "Actualizar usuario", description = "Permite actualizar la información de un usuario específico")
    public ResponseEntity<UsuarioResponse> actualizar(@PathVariable UUID idUsuario,
                                                      @RequestBody @Valid UsuarioRequest request) {
        return ResponseEntity.ok(usuarioService.actualizar(idUsuario, request));
    }

    // PUT /api/usuario/{idUsuario}/desactivar
    @PutMapping("/{idUsuario}/desactivar")
    @Operation(summary = "Desactivar cuenta de usuario", description = "Permite desactivar la cuenta de un usuario específico")
    public ResponseEntity<UsuarioResponse> desactivar(@PathVariable UUID idUsuario) {
        return ResponseEntity.ok(usuarioService.desactivarCuenta(idUsuario));
    }

    // POST /api/usuario/recuperar
    @PostMapping("/recuperar")
    @Operation(summary = "Generar código de recuperación", description = "Permite generar un código de recuperación para restablecer la contraseña")
    public ResponseEntity<String> generarCodigo(@RequestParam String correo) {
        return ResponseEntity.ok(usuarioService.generarCodigoRecuperacion(correo));
    }

    // PUT /api/usuario/recuperar
    @PutMapping("/recuperar")
    @Operation(summary = "Recuperar contraseña", description = "Permite recuperar la contraseña de un usuario específico")
    public ResponseEntity<String> recuperarContrasena(@RequestBody @Valid RecuperarContrasenaRequest request) {
        return ResponseEntity.ok(usuarioService.recuperarContrasena(request));
    }
}