package com.saboresglobales.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.saboresglobales.auth.model.UsuarioModel;
import com.saboresglobales.auth.repository.UsuarioRepository;
import com.saboresglobales.auth.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UsuarioService {
    @Autowired
    private final UsuarioRepository usuarioRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    //registrar un nuevo usuario
    public UsuarioModel registrar(UsuarioModel usuario) {
        //verifica que el correo no este ya registrado
        if (usuarioRepository.existsByCorreo(usuario.getCorreo())) {
            throw new RuntimeException("El correo ya está registrado: " + usuario.getCorreo());
        }
        //encripta la contraseña antes de guardar
        usuario.setContrasena(passwordEncoder.encode(usuario.getContrasena()));
        return usuarioRepository.save(usuario);
    }

    //iniciar sesion -> retorna el token jwt si las credenciales son correctas
    public String login(String correo, String contrasena) {
        UsuarioModel usuario = usuarioRepository.findByCorreo(correo);

        if (usuario == null) {
            throw new RuntimeException("Usuario no encontrado con correo: " + correo);
        }
        if (!usuario.getActivo()) {
            throw new RuntimeException("La cuenta está desactivada");
        }
        //compara la contraseña ingresada con la encriptada en la base de datos
        if (!passwordEncoder.matches(contrasena, usuario.getContrasena())) {
            throw new RuntimeException("Contraseña incorrecta");
        }

        //genera y retorna el token jwt con correo y rol
        return jwtUtil.generarToken(usuario.getCorreo(), usuario.getRol());
    }

    //buscar usuario por id
    public UsuarioModel buscarPorId(UUID idUsuario) {
        return usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado: " + idUsuario));
    }

    //buscar usuario por correo
    public UsuarioModel buscarPorCorreo(String correo) {
        UsuarioModel usuario = usuarioRepository.findByCorreo(correo);
        if (usuario == null) {
            throw new RuntimeException("Usuario no encontrado con correo: " + correo);
        }
        return usuario;
    }

    //listar todos los usuarios, solo para el admin
    public List<UsuarioModel> listarTodos() {
        return usuarioRepository.findAll();
    }

    //actualizar datos del usuario
    public UsuarioModel actualizar(UUID idUsuario, UsuarioModel datosNuevos) {
        UsuarioModel usuario = buscarPorId(idUsuario);
        usuario.setNombre(datosNuevos.getNombre());
        usuario.setCorreo(datosNuevos.getCorreo());
        usuario.setRol(datosNuevos.getRol());
        usuario.setActivo(datosNuevos.getActivo());
        return usuarioRepository.save(usuario);
    }

    //desactivar cuenta (no eliminar)
    public UsuarioModel desactivarCuenta(UUID idUsuario) {
        UsuarioModel usuario = buscarPorId(idUsuario);
        usuario.setActivo(false);
        return usuarioRepository.save(usuario);
    }

    //generar código de recuperacion de contraseña
    public String generarCodigoRecuperacion(String correo) {
        UsuarioModel usuario = buscarPorCorreo(correo);
        //genera un código aleatorio de 6 digitos
        String codigo = String.valueOf((int)(Math.random() * 900000) + 100000);
        usuario.setCodigoRecuperacion(codigo);
        usuarioRepository.save(usuario);
        //en produccion aqui se enviaria el codigo por email, por ahora lo retornamos directamente para poder probarlo
        return codigo;
    }

    //recuperar contraseña usando el codigo
    public String recuperarContrasena(String codigo, String nuevaContrasena) {
        UsuarioModel usuario = usuarioRepository.findByCodigoRecuperacion(codigo);
        if (usuario == null) {
            throw new RuntimeException("Código de recuperación inválido");
        }
        usuario.setContrasena(passwordEncoder.encode(nuevaContrasena));
        usuario.setCodigoRecuperacion(null); // limpia el código después de usarlo
        usuarioRepository.save(usuario);
        return "Contraseña actualizada correctamente";
    }
}