package com.saboresglobales.auth.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.saboresglobales.auth.dto.LoginRequest;
import com.saboresglobales.auth.dto.LoginResponse;
import com.saboresglobales.auth.dto.RecuperarContrasenaRequest;
import com.saboresglobales.auth.dto.UsuarioRequest;
import com.saboresglobales.auth.dto.UsuarioResponse;
import com.saboresglobales.auth.model.UsuarioModel;
import com.saboresglobales.auth.repository.UsuarioRepository;
import com.saboresglobales.auth.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    //convierte model en response
    private UsuarioResponse toResponse(UsuarioModel model) {
        UsuarioResponse response = new UsuarioResponse();
        response.setIdUsuario(model.getIdUsuario());
        response.setNombre(model.getNombre());
        response.setCorreo(model.getCorreo());
        response.setRol(model.getRol());
        response.setActivo(model.getActivo());
        return response;
    }

    //registrar un nuevo usuario
    public UsuarioResponse registrar(UsuarioRequest request) {
        if (usuarioRepository.existsByCorreo(request.getCorreo())) {
            throw new RuntimeException("El correo ya está registrado: " + request.getCorreo());
        }
        UsuarioModel model = new UsuarioModel();
        model.setNombre(request.getNombre());
        model.setCorreo(request.getCorreo());
        model.setContrasena(passwordEncoder.encode(request.getContrasena()));
        model.setRol(request.getRol() != null ? request.getRol() : "CLIENTE");
        return toResponse(usuarioRepository.save(model));
    }

    //inicio de sesion
    public LoginResponse login(LoginRequest request) {
        UsuarioModel usuario = usuarioRepository.findByCorreo(request.getCorreo());

        if (usuario == null) {
            throw new RuntimeException("Usuario no encontrado: " + request.getCorreo());
        }
        if (!usuario.getActivo()) {
            throw new RuntimeException("La cuenta está desactivada");
        }
        if (!passwordEncoder.matches(request.getContrasena(), usuario.getContrasena())) {
            throw new RuntimeException("Contraseña incorrecta");
        }

        //genera el token y arma la respuesta
        String token = jwtUtil.generarToken(usuario.getCorreo(), usuario.getRol());

        LoginResponse response = new LoginResponse();
        response.setToken(token);
        response.setRol(usuario.getRol());
        response.setNombre(usuario.getNombre());
        response.setCorreo(usuario.getCorreo());
        return response;
    }

    //buscar por id
    public UsuarioResponse buscarPorId(UUID idUsuario) {
        return toResponse(usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado: " + idUsuario)));
    }

    //buscar por correo
    public UsuarioResponse buscarPorCorreo(String correo) {
        UsuarioModel usuario = usuarioRepository.findByCorreo(correo);
        if (usuario == null) {
            throw new RuntimeException("Usuario no encontrado: " + correo);
        }
        return toResponse(usuario);
    }

    //listar todos
    public List<UsuarioResponse> listarTodos() {
        return usuarioRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    //actualizar datos
    public UsuarioResponse actualizar(UUID idUsuario, UsuarioRequest request) {
        UsuarioModel model = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado: " + idUsuario));
        model.setNombre(request.getNombre());
        model.setCorreo(request.getCorreo());
        model.setRol(request.getRol());
        return toResponse(usuarioRepository.save(model));
    }

    //desactivar cuenta
    public UsuarioResponse desactivarCuenta(UUID idUsuario) {
        UsuarioModel model = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado: " + idUsuario));
        model.setActivo(false);
        return toResponse(usuarioRepository.save(model));
    }

    //generar codigo de recuperacion de contrasena
    public String generarCodigoRecuperacion(String correo) {
        UsuarioModel usuario = usuarioRepository.findByCorreo(correo);
        if (usuario == null) {
            throw new RuntimeException("Usuario no encontrado: " + correo);
        }
        String codigo = String.valueOf((int)(Math.random() * 900000) + 100000);
        usuario.setCodigoRecuperacion(codigo);
        usuarioRepository.save(usuario);
        //se enviaria por correo de ser real, por ahora solo retornara en el ambiente de pruebas
        return codigo;
    }

    //recuperar contrasena con el codigo
    public String recuperarContrasena(RecuperarContrasenaRequest request) {
        UsuarioModel usuario = usuarioRepository.findByCodigoRecuperacion(request.getCodigo());
        if (usuario == null) {
            throw new RuntimeException("Código de recuperación inválido");
        }
        usuario.setContrasena(passwordEncoder.encode(request.getNuevaContrasena()));
        usuario.setCodigoRecuperacion(null);
        usuarioRepository.save(usuario);
        return "Contraseña actualizada correctamente";
    }
}