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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/usuario")
@RequiredArgsConstructor
@Tag(name = "Usuario", description = "Gestión de usuarios, autenticación y recuperación de contraseña")
public class UsuarioController {

    private final UsuarioService usuarioService;

    @Operation(summary = "Registrar un nuevo usuario")
    @ApiResponse(responseCode = "201", description = "Usuario registrado correctamente")
    @PostMapping("/registro")
    public ResponseEntity<UsuarioResponse> registrar(@RequestBody @Valid UsuarioRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.registrar(request));
    }

    @Operation(summary = "Iniciar sesión", description = "Retorna un token JWT con el rol y datos del usuario")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Login exitoso, retorna token JWT"),
        @ApiResponse(responseCode = "400", description = "Credenciales incorrectas")
    })
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginRequest request) {
        return ResponseEntity.ok(usuarioService.login(request));
    }

    @Operation(summary = "Listar todos los usuarios", description = "Solo accesible con token JWT de ADMIN")
    @ApiResponse(responseCode = "200", description = "Lista obtenida correctamente")
    @GetMapping
    public ResponseEntity<List<UsuarioResponse>> listarTodos() {
        return ResponseEntity.ok(usuarioService.listarTodos());
    }

    @Operation(summary = "Buscar usuario por ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Usuario encontrado"),
        @ApiResponse(responseCode = "400", description = "Usuario no encontrado")
    })
    @GetMapping("/{idUsuario}")
    public ResponseEntity<UsuarioResponse> buscarPorId(@PathVariable UUID idUsuario) {
        return ResponseEntity.ok(usuarioService.buscarPorId(idUsuario));
    }

    @Operation(summary = "Buscar usuario por correo")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Usuario encontrado"),
        @ApiResponse(responseCode = "400", description = "Usuario no encontrado")
    })
    @GetMapping("/correo/{correo}")
    public ResponseEntity<UsuarioResponse> buscarPorCorreo(@PathVariable String correo) {
        return ResponseEntity.ok(usuarioService.buscarPorCorreo(correo));
    }

    @Operation(summary = "Actualizar datos del usuario")
    @ApiResponse(responseCode = "200", description = "Usuario actualizado correctamente")
    @PutMapping("/{idUsuario}")
    public ResponseEntity<UsuarioResponse> actualizar(@PathVariable UUID idUsuario,
                                                      @RequestBody @Valid UsuarioRequest request) {
        return ResponseEntity.ok(usuarioService.actualizar(idUsuario, request));
    }

    @Operation(summary = "Desactivar cuenta de usuario")
    @ApiResponse(responseCode = "200", description = "Cuenta desactivada correctamente")
    @PutMapping("/{idUsuario}/desactivar")
    public ResponseEntity<UsuarioResponse> desactivar(@PathVariable UUID idUsuario) {
        return ResponseEntity.ok(usuarioService.desactivarCuenta(idUsuario));
    }

    @Operation(summary = "Generar código de recuperación de contraseña")
    @ApiResponse(responseCode = "200", description = "Código generado correctamente")
    @PostMapping("/recuperar")
    public ResponseEntity<String> generarCodigo(@RequestParam String correo) {
        return ResponseEntity.ok(usuarioService.generarCodigoRecuperacion(correo));
    }

    @Operation(summary = "Cambiar contraseña con el código de recuperación")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Contraseña actualizada correctamente"),
        @ApiResponse(responseCode = "400", description = "Código inválido")
    })
    @PutMapping("/recuperar")
    public ResponseEntity<String> recuperarContrasena(@RequestBody @Valid RecuperarContrasenaRequest request) {
        return ResponseEntity.ok(usuarioService.recuperarContrasena(request));
    }
}