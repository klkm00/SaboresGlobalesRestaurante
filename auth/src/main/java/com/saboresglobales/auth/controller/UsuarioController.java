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
import lombok.RequiredArgsConstructor;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/usuario")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    // POST /api/usuario/registro
    @PostMapping("/registro")
    public ResponseEntity<UsuarioResponse> registrar(@RequestBody @Valid UsuarioRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.registrar(request));
    }

    // POST /api/usuario/login
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginRequest request) {
        return ResponseEntity.ok(usuarioService.login(request));
    }

    // GET /api/usuario
    @GetMapping
    public ResponseEntity<List<UsuarioResponse>> listarTodos() {
        return ResponseEntity.ok(usuarioService.listarTodos());
    }

    // GET /api/usuario/{idUsuario}
    @GetMapping("/{idUsuario}")
    public ResponseEntity<UsuarioResponse> buscarPorId(@PathVariable UUID idUsuario) {
        return ResponseEntity.ok(usuarioService.buscarPorId(idUsuario));
    }

    // GET /api/usuario/correo/{correo}
    @GetMapping("/correo/{correo}")
    public ResponseEntity<UsuarioResponse> buscarPorCorreo(@PathVariable String correo) {
        return ResponseEntity.ok(usuarioService.buscarPorCorreo(correo));
    }

    // PUT /api/usuario/{idUsuario}
    @PutMapping("/{idUsuario}")
    public ResponseEntity<UsuarioResponse> actualizar(@PathVariable UUID idUsuario,
                                                      @RequestBody @Valid UsuarioRequest request) {
        return ResponseEntity.ok(usuarioService.actualizar(idUsuario, request));
    }

    // PUT /api/usuario/{idUsuario}/desactivar
    @PutMapping("/{idUsuario}/desactivar")
    public ResponseEntity<UsuarioResponse> desactivar(@PathVariable UUID idUsuario) {
        return ResponseEntity.ok(usuarioService.desactivarCuenta(idUsuario));
    }

    // POST /api/usuario/recuperar
    @PostMapping("/recuperar")
    public ResponseEntity<String> generarCodigo(@RequestParam String correo) {
        return ResponseEntity.ok(usuarioService.generarCodigoRecuperacion(correo));
    }

    // PUT /api/usuario/recuperar
    @PutMapping("/recuperar")
    public ResponseEntity<String> recuperarContrasena(@RequestBody @Valid RecuperarContrasenaRequest request) {
        return ResponseEntity.ok(usuarioService.recuperarContrasena(request));
    }
}