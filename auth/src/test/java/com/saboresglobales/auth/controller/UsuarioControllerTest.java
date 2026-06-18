package com.saboresglobales.auth.controller;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import com.saboresglobales.auth.dto.LoginRequest;
import com.saboresglobales.auth.dto.LoginResponse;
import com.saboresglobales.auth.dto.RecuperarContrasenaRequest;
import com.saboresglobales.auth.dto.UsuarioRequest;
import com.saboresglobales.auth.dto.UsuarioResponse;
import com.saboresglobales.auth.service.UsuarioService;
import java.util.List;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsuarioControllerTest {

    @Mock
    private UsuarioService usuarioService;

    @InjectMocks
    private UsuarioController usuarioController;

    private UsuarioResponse usuarioResponse;
    private UsuarioRequest usuarioRequest;
    private LoginRequest loginRequest;
    private LoginResponse loginResponse;
    private UUID idUsuario;

    @BeforeEach
    void setUp() {
        idUsuario = UUID.randomUUID();

        usuarioResponse = new UsuarioResponse();
        usuarioResponse.setIdUsuario(idUsuario);
        usuarioResponse.setNombre("Juan Perez");
        usuarioResponse.setCorreo("juan@test.com");
        usuarioResponse.setRol("CLIENTE");
        usuarioResponse.setActivo(true);

        usuarioRequest = new UsuarioRequest();
        usuarioRequest.setNombre("Juan Perez");
        usuarioRequest.setCorreo("juan@test.com");
        usuarioRequest.setContrasena("123456");
        usuarioRequest.setRol("CLIENTE");

        loginRequest = new LoginRequest();
        loginRequest.setCorreo("juan@test.com");
        loginRequest.setContrasena("123456");

        loginResponse = new LoginResponse();
        loginResponse.setToken("token.jwt.generado");
        loginResponse.setRol("CLIENTE");
        loginResponse.setNombre("Juan Perez");
        loginResponse.setCorreo("juan@test.com");
    }

    @Test
    void cuandoRegistrar_debeRetornar201() {
        when(usuarioService.registrar(usuarioRequest)).thenReturn(usuarioResponse);

        ResponseEntity<UsuarioResponse> respuesta = usuarioController.registrar(usuarioRequest);

        assertEquals(201, respuesta.getStatusCode().value());
        assertNotNull(respuesta.getBody());
        assertEquals("Juan Perez", respuesta.getBody().getNombre());
    }

    @Test
    void cuandoLogin_debeRetornar200ConToken() {
        when(usuarioService.login(loginRequest)).thenReturn(loginResponse);

        ResponseEntity<LoginResponse> respuesta = usuarioController.login(loginRequest);

        assertEquals(200, respuesta.getStatusCode().value());
        assertNotNull(respuesta.getBody());
        assertEquals("token.jwt.generado", respuesta.getBody().getToken());
        assertEquals("CLIENTE", respuesta.getBody().getRol());
    }

    @Test
    void cuandoListarTodos_debeRetornar200() {
        when(usuarioService.listarTodos()).thenReturn(List.of(usuarioResponse));

        ResponseEntity<List<UsuarioResponse>> respuesta = usuarioController.listarTodos();

        assertEquals(200, respuesta.getStatusCode().value());
        assertNotNull(respuesta.getBody());
        assertEquals(1, respuesta.getBody().size());
    }

    @Test
    void cuandoBuscarPorId_debeRetornar200() {
        when(usuarioService.buscarPorId(idUsuario)).thenReturn(usuarioResponse);

        ResponseEntity<UsuarioResponse> respuesta = usuarioController.buscarPorId(idUsuario);

        assertEquals(200, respuesta.getStatusCode().value());
        assertNotNull(respuesta.getBody());
        assertEquals("juan@test.com", respuesta.getBody().getCorreo());
    }

    @Test
    void cuandoBuscarPorCorreo_debeRetornar200() {
        when(usuarioService.buscarPorCorreo("juan@test.com")).thenReturn(usuarioResponse);

        ResponseEntity<UsuarioResponse> respuesta = usuarioController.buscarPorCorreo("juan@test.com");

        assertEquals(200, respuesta.getStatusCode().value());
        assertNotNull(respuesta.getBody());
    }

    @Test
    void cuandoActualizar_debeRetornar200() {
        when(usuarioService.actualizar(idUsuario, usuarioRequest)).thenReturn(usuarioResponse);

        ResponseEntity<UsuarioResponse> respuesta = usuarioController.actualizar(idUsuario, usuarioRequest);

        assertEquals(200, respuesta.getStatusCode().value());
        assertNotNull(respuesta.getBody());
    }

    @Test
    void cuandoDesactivar_debeRetornar200() {
        usuarioResponse.setActivo(false);
        when(usuarioService.desactivarCuenta(idUsuario)).thenReturn(usuarioResponse);

        ResponseEntity<UsuarioResponse> respuesta = usuarioController.desactivar(idUsuario);

        assertEquals(200, respuesta.getStatusCode().value());
        assertFalse(respuesta.getBody().getActivo());
    }

    @Test
    void cuandoGenerarCodigo_debeRetornar200() {
        when(usuarioService.generarCodigoRecuperacion("juan@test.com")).thenReturn("123456");

        ResponseEntity<String> respuesta = usuarioController.generarCodigo("juan@test.com");

        assertEquals(200, respuesta.getStatusCode().value());
        assertEquals("123456", respuesta.getBody());
    }

    @Test
    void cuandoRecuperarContrasena_debeRetornar200() {
        RecuperarContrasenaRequest request = new RecuperarContrasenaRequest();
        request.setCodigo("123456");
        request.setNuevaContrasena("nueva123");

        when(usuarioService.recuperarContrasena(request))
                .thenReturn("Contraseña actualizada correctamente");

        ResponseEntity<String> respuesta = usuarioController.recuperarContrasena(request);

        assertEquals(200, respuesta.getStatusCode().value());
        assertEquals("Contraseña actualizada correctamente", respuesta.getBody());
    }
}