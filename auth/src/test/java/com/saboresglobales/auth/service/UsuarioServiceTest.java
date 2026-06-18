package com.saboresglobales.auth.service;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.saboresglobales.auth.dto.LoginRequest;
import com.saboresglobales.auth.dto.LoginResponse;
import com.saboresglobales.auth.dto.RecuperarContrasenaRequest;
import com.saboresglobales.auth.dto.UsuarioRequest;
import com.saboresglobales.auth.dto.UsuarioResponse;
import com.saboresglobales.auth.model.UsuarioModel;
import com.saboresglobales.auth.repository.UsuarioRepository;
import com.saboresglobales.auth.security.JwtUtil;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private UsuarioService usuarioService;

    private UsuarioModel usuarioModel;
    private UsuarioRequest usuarioRequest;
    private LoginRequest loginRequest;

    @BeforeEach
    void setUp() {
        usuarioModel = new UsuarioModel();
        usuarioModel.setIdUsuario(UUID.randomUUID());
        usuarioModel.setNombre("Juan Pérez");
        usuarioModel.setCorreo("juan@test.com");
        usuarioModel.setContrasena("encriptada123");
        usuarioModel.setRol("CLIENTE");
        usuarioModel.setActivo(true);

        usuarioRequest = new UsuarioRequest();
        usuarioRequest.setNombre("Juan Pérez");
        usuarioRequest.setCorreo("juan@test.com");
        usuarioRequest.setContrasena("123456");
        usuarioRequest.setRol("CLIENTE");

        loginRequest = new LoginRequest();
        loginRequest.setCorreo("juan@test.com");
        loginRequest.setContrasena("123456");
    }

    @Test
    void cuandoRegistrar_debeRetornarUsuarioResponse() {
        when(usuarioRepository.existsByCorreo(usuarioRequest.getCorreo())).thenReturn(false);
        when(passwordEncoder.encode(usuarioRequest.getContrasena())).thenReturn("encriptada123");
        when(usuarioRepository.save(any(UsuarioModel.class))).thenReturn(usuarioModel);

        UsuarioResponse resultado = usuarioService.registrar(usuarioRequest);

        assertNotNull(resultado);
        assertEquals("Juan Pérez", resultado.getNombre());
        assertEquals("juan@test.com", resultado.getCorreo());
        assertEquals("CLIENTE", resultado.getRol());
    }

    @Test
    void cuandoRegistrarCorreoExistente_debeLanzarExcepcion() {
        when(usuarioRepository.existsByCorreo(usuarioRequest.getCorreo())).thenReturn(true);

        assertThrows(RuntimeException.class, () -> usuarioService.registrar(usuarioRequest));
    }

    @Test
    void cuandoLoginCorrecto_debeRetornarLoginResponse() {
        when(usuarioRepository.findByCorreo(loginRequest.getCorreo())).thenReturn(usuarioModel);
        when(passwordEncoder.matches(loginRequest.getContrasena(), usuarioModel.getContrasena()))
                .thenReturn(true);
        when(jwtUtil.generarToken(usuarioModel.getCorreo(), usuarioModel.getRol()))
                .thenReturn("token.jwt.generado");

        LoginResponse resultado = usuarioService.login(loginRequest);

        assertNotNull(resultado);
        assertEquals("token.jwt.generado", resultado.getToken());
        assertEquals("CLIENTE", resultado.getRol());
        assertEquals("Juan Pérez", resultado.getNombre());
    }

    @Test
    void cuandoLoginUsuarioNoExiste_debeLanzarExcepcion() {
        when(usuarioRepository.findByCorreo(any(String.class))).thenReturn(null);

        assertThrows(RuntimeException.class, () -> usuarioService.login(loginRequest));
    }

    @Test
    void cuandoLoginContrasenaIncorrecta_debeLanzarExcepcion() {
        when(usuarioRepository.findByCorreo(loginRequest.getCorreo())).thenReturn(usuarioModel);
        when(passwordEncoder.matches(any(String.class), any(String.class))).thenReturn(false);

        assertThrows(RuntimeException.class, () -> usuarioService.login(loginRequest));
    }

    @Test
    void cuandoLoginCuentaDesactivada_debeLanzarExcepcion() {
        usuarioModel.setActivo(false);
        when(usuarioRepository.findByCorreo(loginRequest.getCorreo())).thenReturn(usuarioModel);

        assertThrows(RuntimeException.class, () -> usuarioService.login(loginRequest));
    }

    @Test
    void cuandoBuscarPorIdExiste_debeRetornarUsuarioResponse() {
        UUID id = usuarioModel.getIdUsuario();
        when(usuarioRepository.findById(id)).thenReturn(Optional.of(usuarioModel));

        UsuarioResponse resultado = usuarioService.buscarPorId(id);

        assertNotNull(resultado);
        assertEquals("juan@test.com", resultado.getCorreo());
    }

    @Test
    void cuandoBuscarPorIdNoExiste_debeLanzarExcepcion() {
        when(usuarioRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> usuarioService.buscarPorId(UUID.randomUUID()));
    }

    @Test
    void cuandoListarTodos_debeRetornarLista() {
        when(usuarioRepository.findAll()).thenReturn(List.of(usuarioModel));

        List<UsuarioResponse> resultado = usuarioService.listarTodos();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Juan Pérez", resultado.get(0).getNombre());
    }

    @Test
    void cuandoDesactivarCuenta_debeRetornarUsuarioDesactivado() {
        UUID id = usuarioModel.getIdUsuario();
        when(usuarioRepository.findById(id)).thenReturn(Optional.of(usuarioModel));
        when(usuarioRepository.save(any(UsuarioModel.class))).thenAnswer(invocation -> {
            return invocation.getArgument(0);
        });

        UsuarioResponse resultado = usuarioService.desactivarCuenta(id);

        assertNotNull(resultado);
        assertFalse(resultado.getActivo());
    }

    @Test
    void cuandoGenerarCodigoRecuperacion_debeRetornarCodigo() {
        when(usuarioRepository.findByCorreo(usuarioModel.getCorreo())).thenReturn(usuarioModel);
        when(usuarioRepository.save(any(UsuarioModel.class))).thenReturn(usuarioModel);

        String codigo = usuarioService.generarCodigoRecuperacion(usuarioModel.getCorreo());

        assertNotNull(codigo);
        assertEquals(6, codigo.length());
    }

    @Test
    void cuandoRecuperarContrasena_debeActualizarContrasena() {
        RecuperarContrasenaRequest request = new RecuperarContrasenaRequest();
        request.setCodigo("123456");
        request.setNuevaContrasena("nueva123");

        usuarioModel.setCodigoRecuperacion("123456");
        when(usuarioRepository.findByCodigoRecuperacion("123456")).thenReturn(usuarioModel);
        when(passwordEncoder.encode("nueva123")).thenReturn("nuevaEncriptada");
        when(usuarioRepository.save(any(UsuarioModel.class))).thenReturn(usuarioModel);

        String resultado = usuarioService.recuperarContrasena(request);

        assertEquals("Contraseña actualizada correctamente", resultado);
        assertNull(usuarioModel.getCodigoRecuperacion());
    }
}