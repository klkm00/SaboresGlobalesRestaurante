package com.saboresglobales.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.saboresglobales.auth.model.UsuarioModel;
import com.saboresglobales.auth.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/usuario")
@RequiredArgsConstructor
public class UsuarioController {
    @Autowired
    private final UsuarioService usuarioService;

    // POST /api/usuario/registro - Registrar un nuevo usuario
    @PostMapping("/registro")
    public ResponseEntity<UsuarioModel> registrar(@RequestBody @Valid UsuarioModel usuario) {
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.registrar(usuario));
    }

    // POST /api/usuario/login - Iniciar sesión, retorna el token JWT
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam String correo,
                                        @RequestParam String contrasena) {
        return ResponseEntity.ok(usuarioService.login(correo, contrasena));
    }

    // GET /api/usuario - Listar todos (solo ADMIN)
    @GetMapping
    public ResponseEntity<List<UsuarioModel>> listarTodos() {
        return ResponseEntity.ok(usuarioService.listarTodos());
    }

    // GET /api/usuario/{idUsuario} - Buscar por ID
    @GetMapping("/{idUsuario}")
    public ResponseEntity<UsuarioModel> buscarPorId(@PathVariable UUID idUsuario) {
        return ResponseEntity.ok(usuarioService.buscarPorId(idUsuario));
    }

    // GET /api/usuario/correo/{correo} - Buscar por correo
    @GetMapping("/correo/{correo}")
    public ResponseEntity<UsuarioModel> buscarPorCorreo(@PathVariable String correo) {
        return ResponseEntity.ok(usuarioService.buscarPorCorreo(correo));
    }

    // PUT /api/usuario/{idUsuario} - Actualizar datos
    @PutMapping("/{idUsuario}")
    public ResponseEntity<UsuarioModel> actualizar(@PathVariable UUID idUsuario,
                                                   @RequestBody @Valid UsuarioModel usuario) {
        return ResponseEntity.ok(usuarioService.actualizar(idUsuario, usuario));
    }

    // PUT /api/usuario/{idUsuario}/desactivar - Desactivar cuenta
    @PutMapping("/{idUsuario}/desactivar")
    public ResponseEntity<UsuarioModel> desactivar(@PathVariable UUID idUsuario) {
        return ResponseEntity.ok(usuarioService.desactivarCuenta(idUsuario));
    }

    // POST /api/usuario/recuperar - Generar código de recuperación
    @PostMapping("/recuperar")
    public ResponseEntity<String> generarCodigo(@RequestParam String correo) {
        return ResponseEntity.ok(usuarioService.generarCodigoRecuperacion(correo));
    }

    // PUT /api/usuario/recuperar - Cambiar contraseña con el código
    @PutMapping("/recuperar")
    public ResponseEntity<String> recuperarContrasena(@RequestParam String codigo,
                                                      @RequestParam String nuevaContrasena) {
        return ResponseEntity.ok(usuarioService.recuperarContrasena(codigo, nuevaContrasena));
    }
}