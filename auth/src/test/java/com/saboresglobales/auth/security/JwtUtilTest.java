package com.saboresglobales.auth.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class JwtUtilTest {

    private JwtUtil jwtUtil;

    @BeforeEach
    void setUp() {
        jwtUtil = new JwtUtil();
        // Inyectamos los valores del application.properties manualmente
        ReflectionTestUtils.setField(jwtUtil, "secret", "sabores-globales-clave-secreta-super-segura-2024");
        ReflectionTestUtils.setField(jwtUtil, "expiration", 86400000L);
    }

    @Test
    void cuandoGenerarToken_debeRetornarTokenNoNulo() {
        String token = jwtUtil.generarToken("juan@test.com", "CLIENTE");

        assertNotNull(token);
        assertFalse(token.isEmpty());
    }

    @Test
    void cuandoObtenerCorreo_debeRetornarCorreoCorrecto() {
        String token = jwtUtil.generarToken("juan@test.com", "CLIENTE");

        String correo = jwtUtil.obtenerCorreo(token);

        assertEquals("juan@test.com", correo);
    }

    @Test
    void cuandoObtenerRol_debeRetornarRolCorrecto() {
        String token = jwtUtil.generarToken("juan@test.com", "ADMIN");

        String rol = jwtUtil.obtenerRol(token);

        assertEquals("ADMIN", rol);
    }

    @Test
    void cuandoValidarTokenValido_debeRetornarTrue() {
        String token = jwtUtil.generarToken("juan@test.com", "CLIENTE");

        boolean valido = jwtUtil.validarToken(token);

        assertTrue(valido);
    }

    @Test
    void cuandoValidarTokenInvalido_debeRetornarFalse() {
        boolean valido = jwtUtil.validarToken("token.invalido.xyz");

        assertFalse(valido);
    }
}